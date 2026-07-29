package com.project.referral;

import com.project.referral.config.RagProperties;
import com.project.referral.dto.response.ResumeChunk;
import com.project.referral.service.SemanticChunkingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
public class ChunkingServiceTests {
    private SemanticChunkingService chunker;

   @BeforeEach
    void setup() {
        RagProperties props = new RagProperties();
        props.getChunk().setSize(100); // small for tests
        props.getChunk().setOverlap(20);
        chunker = new SemanticChunkingService(props);
    }

    @Test
    void chunks_short_text_into_single_chunk() {
        String text = "This is a short resume paragraph. It should be one chunk.";
        List<ResumeChunk> chunks = chunker.chunk(text, "r1");
        assertThat(chunks).hasSize(1);
        assertThat(chunks.get(0).getText()).contains("short resume paragraph");
    }

    @Test
    void splits_long_paragraph_respecting_sentence_boundaries() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) sb.append("Sentence " + i + ". This is detail for sentence " + i + ". ");
        String text = sb.toString();
        List<ResumeChunk> chunks = chunker.chunk(text, "r2");
        assertThat(chunks.size()).isGreaterThan(1);
        for (ResumeChunk c : chunks) {
            assertThat(c.getText().length()).isLessThanOrEqualTo(120);
        }
    }
}
