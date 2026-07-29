package com.project.referral.service;

import com.project.referral.client.ChromaClient;
import com.project.referral.config.RagProperties;
import com.project.referral.dto.response.ResumeChunk;
import com.project.referral.dto.response.JobRequirements;
import com.project.referral.exception.VectorSearchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChromaResumeRetriever implements ResumeRetriever {
    private final ChromaClient chromaClient;
    private final EmbeddingService embeddingService;
    private final RagProperties ragProperties;
    private final String collection = "resumes";
    @Override    public List<ResumeChunk> search(JobRequirements requirements, int topK) {        try {            // build search query from technical requirements            List<String> parts = new ArrayList<>();            if (requirements.getRequiredSkills() != null) parts.addAll(requirements.getRequiredSkills());            if (requirements.getFrameworks() != null) parts.addAll(requirements.getFrameworks());            if (requirements.getTools() != null) parts.addAll(requirements.getTools());            if (requirements.getDatabases() != null) parts.addAll(requirements.getDatabases());            String query = parts.stream().filter(Objects::nonNull).collect(Collectors.joining(" "));            if (query.isBlank()) query = requirements.getJobTitle() != null ? requirements.getJobTitle() : "";            List<Double> queryEmbedding;
            try {
                queryEmbedding = embeddingService.generateEmbedding(query);
            } catch (Exception e) {
                // embeddings not available (e.g., SDK incompatibility) — skip vector search and return empty evidence
                log.warn("Embeddings unavailable, skipping Chroma retrieval: {}", e.getMessage());
                return List.of();
            }            ChromaClient.QueryResult result = chromaClient.queryByEmbedding(collection, queryEmbedding, topK);            if (result == null || CollectionUtils.isEmpty(result.documents)) return List.of();            // flatten first row (we asked single query) -> result.documents.get(0) etc            List<String> docs = result.documents.get(0);            List<Double> distances = result.distances != null ? result.distances.get(0) : List.of();            List<Map<String,Object>> metadatas = result.metadatas != null ? result.metadatas.get(0) : List.of();            double similarityThreshold = ragProperties.getSimilarityThreshold();            List<ResumeChunk> chunks = new ArrayList<>();            for (int i = 0; i < docs.size() && i < distances.size(); i++) {                double distance = distances.get(i) instanceof Number ? ((Number) distances.get(i)).doubleValue() : Double.parseDouble(String.valueOf(distances.get(i)));                // Chroma distance meaning depends on metric; assume smaller is more similar — convert to similarity roughly                double similarity = 1.0 - distance; // heuristic                if (similarity < similarityThreshold) continue;                Map<String,Object> meta = metadatas.size() > i ? metadatas.get(i) : Map.of();                ResumeChunk rc = new ResumeChunk();                rc.setText(docs.get(i));                rc.setResumeId(meta.getOrDefault("resumeId", "").toString());                rc.setCandidateId(meta.getOrDefault("candidateId", "").toString());                Object cn = meta.get("chunkNumber");                if (cn instanceof Number) rc.setChunkNumber(((Number) cn).intValue());                chunks.add(rc);            }            // dedupe by text and merge overlapping (simple dedupe)            Map<String, ResumeChunk> dedup = new LinkedHashMap<>();            for (ResumeChunk c : chunks) if (!dedup.containsKey(c.getText())) dedup.put(c.getText(), c);            List<ResumeChunk> finalChunks = new ArrayList<>(dedup.values());            log.info("Retrieved {} chunks from Chroma (post-filter {})", finalChunks.size(), collection);            return finalChunks;        } catch (Exception e) {            throw new VectorSearchException("Failed to retrieve from Chroma: " + e.getMessage(), e);        }    }}
