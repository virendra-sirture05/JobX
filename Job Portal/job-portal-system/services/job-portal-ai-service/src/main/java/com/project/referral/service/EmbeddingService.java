package com.project.referral.service;

import com.project.referral.exception.EmbeddingException;

import java.util.List;

public interface EmbeddingService {

    List<Double> generateEmbedding(String text) throws EmbeddingException;

    List<List<Double>> generateEmbeddings(List<String> texts) throws EmbeddingException;
}
