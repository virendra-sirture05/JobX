package com.project.referral;

import com.project.referral.service.GoogleEmbeddingService;
import com.project.referral.config.GeminiProperties;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GoogleEmbeddingServiceExtractionTest {

    @Test
    void extractVectors_fromResponse_withGetDataAndGetEmbedding() throws Exception {
        // prepare fake response object with getData() -> list of items with getEmbedding()
        class Item {
            private final List<Double> embedding;
            Item(List<Double> e) { this.embedding = e; }
            public List<Double> getEmbedding() { return embedding; }
        }
        class Resp {
            private final List<Item> data;
            Resp(List<Item> data) { this.data = data; }
            public List<Item> getData() { return data; }
        }

       List<Item> items = new ArrayList<>();
        items.add(new Item(List.of(0.1, 0.2, 0.3)));
        items.add(new Item(List.of(0.4, 0.5)));
        Resp resp = new Resp(items);

        
        // instantiate service with null client and props (not used for private method)
        
        GoogleEmbeddingService svc = new GoogleEmbeddingService(null, new GeminiProperties());

        Method m = GoogleEmbeddingService.class.getDeclaredMethod("extractVectorsFromResponse", Object.class);
        m.setAccessible(true);
        Object result = m.invoke(svc, resp);
        @SuppressWarnings("unchecked")
        List<List<Double>> vectors = (List<List<Double>>) result;
        assertThat(vectors).hasSize(2);
        assertThat(vectors.get(0)).containsExactly(0.1, 0.2, 0.3);
    }

    @Test
    void extractVectors_fromResponse_withPlainList() throws Exception {
        
        List<List<Double>> plain = new ArrayList<>();
        plain.add(List.of(1.0, 2.0));
        plain.add(List.of(3.0));

        GoogleEmbeddingService svc = new GoogleEmbeddingService(null, new GeminiProperties());
        Method m = GoogleEmbeddingService.class.getDeclaredMethod("extractVectorsFromResponse", Object.class);
        m.setAccessible(true);
        Object result = m.invoke(svc, plain);
        @SuppressWarnings("unchecked")
        List<List<Double>> vectors = (List<List<Double>>) result;
        assertThat(vectors).hasSize(2);
        assertThat(vectors.get(1)).containsExactly(3.0);
    }
}
