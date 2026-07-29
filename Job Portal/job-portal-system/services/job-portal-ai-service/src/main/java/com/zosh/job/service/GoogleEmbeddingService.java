package com.project.referral.service;

import com.project.referral.config.GeminiProperties;
import com.project.referral.exception.EmbeddingException;
import com.google.genai.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleEmbeddingService implements EmbeddingService {

    private final Client genAiClient;
    private final GeminiProperties props;

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleEmbeddingService.class);

    @Override
    public List<Double> generateEmbedding(String text) throws EmbeddingException {
        List<List<Double>> all = generateEmbeddings(List.of(text));
        return all.isEmpty() ? List.of() : all.get(0);
    }

   @Override
    public List<List<Double>> generateEmbeddings(List<String> texts) throws EmbeddingException {
        long start = System.currentTimeMillis();
        try {
            // Use reflection to call the embeddings API to avoid SDK version coupling.
            Object embeddingsClient = callNoArgsMethod(genAiClient, "embeddings");
            if (embeddingsClient == null) {
                throw new EmbeddingException("Embeddings API not available on genAiClient");
            }

           // try common method signatures: create(model, input) or embed(model, input)
            Method createMethod = findMethod(embeddingsClient.getClass(), new String[]{"create", "embed", "embedBatch"}, String.class, List.class);
            if (createMethod == null) {
                // last resort: try a single-arg method that accepts a request object (not handled)
                throw new EmbeddingException("Unsupported embeddings client API. Please verify google-genai SDK version.");
            }

          Object response = createMethod.invoke(embeddingsClient, props.getEmbeddingModel(), texts);
            List<List<Double>> vectors = extractVectorsFromResponse(response);
            long duration = System.currentTimeMillis() - start;
            LOGGER.info("Embedding generation time: {} ms for {} inputs", duration, texts.size());
            return vectors;
        } catch (EmbeddingException e) {
            throw e;
        } catch (Exception e) {
            throw new EmbeddingException("Failed to generate embeddings: " + e.getMessage(), e);
        }
    }

    private Object callNoArgsMethod(Object target, String methodName) {
        try {
            Method m = target.getClass().getMethod(methodName);
            return m.invoke(target);
        } catch (NoSuchMethodException ignored) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Method findMethod(Class<?> clazz, String[] names, Class<?>... paramTypes) {
        for (String n : names) {
            try {
                return clazz.getMethod(n, paramTypes);
            } catch (NoSuchMethodException ignored) {
            }
        }
        return null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private List<List<Double>> extractVectorsFromResponse(Object response) throws EmbeddingException {
        if (response == null) {
            throw new EmbeddingException("Empty embeddings response");
        }
        try {
            // Common shapes: response.getData() -> List<Item> where Item.getEmbedding() -> List<Double>
            Method[] candidates = new Method[]{
                    safeGetMethod(response.getClass(), "data"),
                    safeGetMethod(response.getClass(), "getData"),
                    safeGetMethod(response.getClass(), "embeddings"),
                    safeGetMethod(response.getClass(), "getEmbeddings")
            };

            Object data = null;
            for (Method m : candidates) {
                if (m != null) {
                    data = m.invoke(response);
                    if (data != null) break;
                }
            }

            if (data == null) {
                // maybe response is already a list (some SDKs return a list directly)
                if (response instanceof List) {
                    data = response;
                } else {
                    throw new EmbeddingException("Unable to extract embeddings list from response");
                }
            }

            List<?> items = (List<?>) data;
            List<List<Double>> result = new ArrayList<>();
            for (Object item : items) {
                if (item == null) continue;
                // try item.getEmbedding() or item.embedding()
                Method embMethod = safeGetMethod(item.getClass(), "embedding");
                if (embMethod == null) embMethod = safeGetMethod(item.getClass(), "getEmbedding");
                Object embObj = embMethod != null ? embMethod.invoke(item) : null;

                if (embObj == null) {
                    // maybe item is a list of doubles already
                    if (item instanceof List) {
                        result.add((List<Double>) item);
                        continue;
                    }
                    throw new EmbeddingException("Embedding item does not contain embedding vector");
                }
                // ensure List<Double>
                List<Double> vec = new ArrayList<>();
                if (embObj instanceof List) {
                    for (Object v : (List) embObj) {
                        if (v == null) continue;
                        if (v instanceof Number) vec.add(((Number) v).doubleValue());
                        else vec.add(Double.parseDouble(String.valueOf(v)));
                    }
                } else if (embObj.getClass().isArray()) {
                    Object[] arr = (Object[]) embObj;
                    for (Object v : arr) vec.add(((Number) v).doubleValue());
                } else {
                    throw new EmbeddingException("Unsupported embedding vector type: " + embObj.getClass());
                }
                result.add(vec);
            }
            return result;
        } catch (EmbeddingException e) {
            throw e;
        } catch (Exception e) {
            throw new EmbeddingException("Error extracting embeddings: " + e.getMessage(), e);
        }
    }

    private Method safeGetMethod(Class<?> clazz, String name) {
        try {
            return clazz.getMethod(name);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
