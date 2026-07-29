package com.project.referral.service;

import com.project.referral.client.ChromaClient;
import com.project.referral.dto.response.ResumeChunk;
import com.project.referral.exception.ResumeIndexingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChromaResumeIndexer implements ResumeIndexer {
    private final ChromaClient chromaClient;
    private final EmbeddingService embeddingService;
    private final String collection = "resumes"; // could be configurable via ChromaProperties    @Override    public void indexResume(String resumeId, String candidateId, List<ResumeChunk> chunks) {        try {            List<String> ids = new ArrayList<>();            List<List<Double>> embeddings = new ArrayList<>();            List<Map<String,Object>> metadatas = new ArrayList<>();            List<String> docs = new ArrayList<>();            List<String> texts = chunks.stream().map(ResumeChunk::getText).collect(Collectors.toList());            List<List<Double>> vectors = embeddingService.generateEmbeddings(texts);            for (int i = 0; i < chunks.size(); i++) {                ResumeChunk c = chunks.get(i);                ids.add(resumeId + "_" + c.getChunkNumber());                embeddings.add(vectors.get(i));                Map<String,Object> meta = new HashMap<>();                meta.put("resumeId", resumeId);                meta.put("candidateId", candidateId);                meta.put("chunkNumber", c.getChunkNumber());                meta.put("startIndex", c.getStartIndex());                meta.put("endIndex", c.getEndIndex());                meta.put("pageNumber", c.getPageNumber());                meta.put("section", c.getSection());                metadatas.add(meta);                docs.add(c.getText());            }            chromaClient.upsert(collection, ids, embeddings, metadatas, docs);            log.info("Indexed {} chunks for resume {}", chunks.size(), resumeId);        } catch (Exception e) {            throw new ResumeIndexingException("Failed to index resume: " + e.getMessage(), e);        }    }
    @Override    public void updateResume(String resumeId, List<ResumeChunk> chunks) {        indexResume(resumeId, null, chunks);    }    @Override    public void deleteResume(String resumeId) {        throw new ResumeIndexingException("Delete not implemented; implement via Chroma API if required");    }}