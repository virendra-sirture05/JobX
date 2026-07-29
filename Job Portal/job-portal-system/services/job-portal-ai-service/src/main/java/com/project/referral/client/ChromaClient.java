package com.project.referral.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.referral.config.ChromaProperties;
import com.project.referral.exception.VectorSearchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChromaClient {
    private final RestTemplate restTemplate;
    private final ChromaProperties props;
    private final ObjectMapper objectMapper;
    // Minimal upsert using common Chroma HTTP API shape: /collections/{collection}/add or /collections/{collection}/items?upsert=true    public void upsert(String collection, List<String> ids, List<List<Double>> embeddings, List<Map<String,Object>> metadatas, List<String> documents) {
        try {            String url = props.getUrl();            if (!url.endsWith("/")) url += "/";            String endpoint = url + "collections/" + collection + "/add"; // widely-used shape            Map<String,Object> body = new HashMap<>();            body.put("ids", ids);            body.put("embeddings", embeddings);            body.put("metadatas", metadatas);            body.put("documents", documents);            HttpHeaders headers = new HttpHeaders();            headers.setContentType(MediaType.APPLICATION_JSON);            HttpEntity<String> req = new HttpEntity<>(objectMapper.writeValueAsString(body), headers);            String resp = restTemplate.postForObject(endpoint, req, String.class);            log.debug("Chroma upsert response: {}", resp);        } catch (Exception e) {            throw new VectorSearchException("Failed to upsert to Chroma: " + e.getMessage(), e);        }    }
    @JsonIgnoreProperties(ignoreUnknown = true)    public static class QueryResult {        public List<List<Double>> distances;        public List<List<String>> ids;        public List<List<Map<String,Object>>> metadatas;        public List<List<String>> documents;    }    public QueryResult queryByEmbedding(String collection, List<Double> queryEmbedding, int nResults) {        try {            String url = props.getUrl();            if (!url.endsWith("/")) url += "/";            String endpoint = url + "collections/" + collection + "/query";            Map<String,Object> body = new HashMap<>();            body.put("query_embeddings", List.of(queryEmbedding));            body.put("n_results", nResults);            body.put("include", List.of("distances","documents","metadatas","ids"));            HttpHeaders headers = new HttpHeaders();            headers.setContentType(MediaType.APPLICATION_JSON);            String payload = objectMapper.writeValueAsString(body);            HttpEntity<String> req = new HttpEntity<>(payload, headers);            String resp = restTemplate.postForObject(endpoint, req, String.class);            JsonNode node = objectMapper.readTree(resp);            QueryResult res = new QueryResult();            // parse arrays            res.distances = objectMapper.convertValue(node.path("distances"), List.class);            res.ids = objectMapper.convertValue(node.path("ids"), List.class);            res.metadatas = objectMapper.convertValue(node.path("metadatas"), List.class);            res.documents = objectMapper.convertValue(node.path("documents"), List.class);            return res;        } catch (Exception e) {            throw new VectorSearchException("Chroma query failed: " + e.getMessage(), e);        }    }
}
