package com.project.referral.service;

import com.project.referral.config.RagProperties;
import com.project.referral.dto.response.ResumeChunk;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class SemanticChunkingService implements ChunkingService {

    private final RagProperties ragProperties;
    @Override
    public List<ResumeChunk> chunk(String text, String resumeId) {
        int chunkSize = ragProperties.getChunk().getSize();
        int overlap = ragProperties.getChunk().getOverlap();
        if (chunkSize <= 0) chunkSize = 600;
        if (overlap < 0) overlap = 100;
        List<ResumeChunk> chunks = new ArrayList<>();
        if (!StringUtils.hasText(text)) return chunks;
        // Normalize whitespace
        String normalized = text.replaceAll("\r\n", "\n").trim();
        // Split into paragraphs (preserve headings and bullet blocks)
        String[] paragraphs = normalized.split("\n\s*\n");
        int chunkNum = 0;
        int globalIndex = 0;
        for (String para : paragraphs) {
            String p = para.trim();
            if (p.isEmpty()) {
                globalIndex += para.length() + 2; // account for separator
                continue;
            }
            // If paragraph is short, try to merge into a buffer that fits chunkSize when possible
            if (p.length() <= chunkSize) {
                // create single chunk for this paragraph (but may merge with previous if space available)
                int start = normalized.indexOf(p, globalIndex);
                int end = start + p.length();
                ResumeChunk rc = ResumeChunk.builder()
                        .resumeId(resumeId)
                        .chunkNumber(chunkNum++)
                        .startIndex(start)
                        .endIndex(end)
                        .text(p)
                        .build();
                chunks.add(rc);
                globalIndex = end + 2;
                continue;
            }
            // Paragraph longer than chunkSize: split by sentences (prefer) or newline boundaries
            int paraIndex = 0;
            while (paraIndex < p.length()) {
                int remaining = p.length() - paraIndex;
                int take = Math.min(chunkSize, remaining);
                int windowStart = paraIndex;
                int windowEnd = paraIndex + take;
                // try to break at last sentence end within windowEnd ('.','?','!')
                int breakPos = -1;
                for (int i = windowEnd - 1; i > windowStart; i--) {
                    char c = p.charAt(i);
                    if (c == '.' || c == '?' || c == '!') {
                        breakPos = i + 1; // include punctuation
                        break;
                    }
                    // prefer newline boundaries too
                    if (c == '\n') {
                        breakPos = i + 1;
                        break;
                    }
                }
                if (breakPos == -1) {
                    // cannot find nice break; hard cut at windowEnd
                    breakPos = windowEnd;
                }
                String chunkText = p.substring(windowStart, breakPos).trim();
                int start = normalized.indexOf(chunkText, globalIndex);
                if (start < 0) start = globalIndex; // fallback
                int end = Math.min(start + chunkText.length(), normalized.length());

                ResumeChunk rc = ResumeChunk.builder()
                        .resumeId(resumeId)
                        .chunkNumber(chunkNum++)
                        .startIndex(start)
                        .endIndex(end)
                        .text(chunkText)
                        .build();
                chunks.add(rc);
                // advance with overlap
                int advance = Math.max(0, breakPos - overlap);
                paraIndex = advance;
                globalIndex = end;
            }
        }
        log.info("Chunking produced {} chunks for resumeId={}", chunks.size(), resumeId);
        return mergeSmallAdjacentChunks(chunks, chunkSize);
    }
    // merge adjacent tiny chunks to avoid a flood of tiny chunks
    private List<ResumeChunk> mergeSmallAdjacentChunks(List<ResumeChunk> chunks, int chunkSize) {
        if (chunks.isEmpty()) return chunks;
        List<ResumeChunk> merged = new ArrayList<>();
        ResumeChunk buffer = null;
        for (ResumeChunk c : chunks) {
            if (buffer == null) {
                buffer = c;
                continue;
            }
            if (buffer.getText().length() < chunkSize / 4 && (buffer.getText().length() + c.getText().length()) <= chunkSize) {
                // merge c into buffer
                String combined = buffer.getText() + "\n\n" + c.getText();
                buffer.setText(combined);
                buffer.setEndIndex(c.getEndIndex());
            } else {
                merged.add(buffer);
                buffer = c;
            }
        }
        if (buffer != null) merged.add(buffer);
        return merged;
    }
}
