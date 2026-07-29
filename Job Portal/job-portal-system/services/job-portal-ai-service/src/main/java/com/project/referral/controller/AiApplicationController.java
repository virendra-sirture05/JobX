package com.project.referral.controller;

import com.project.referral.common.dto.response.ApiResponse;
import com.project.referral.dto.request.BulkScreeningRequest;
import com.project.referral.dto.request.CoverLetterRequest;
import com.project.referral.dto.request.InterviewQuestionsRequest;
import com.project.referral.dto.request.ScreeningScoreRequest;
import com.project.referral.dto.request.SkillsGapRequest;
import com.project.referral.dto.response.AiTextResponse;
import com.project.referral.dto.response.BulkScreeningResponse;
import com.project.referral.dto.response.InterviewQuestionsResponse;
import com.project.referral.dto.response.ScreeningScoreResponse;
import com.project.referral.dto.response.SkillsGapResponse;
import com.project.referral.service.ApplicationAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai/application")
@RequiredArgsConstructor
public class AiApplicationController {

    private final ApplicationAiService applicationAiService;


    @PostMapping("/cover-letter")
    public ResponseEntity<ApiResponse<AiTextResponse>> generateCoverLetter(
            @Valid @RequestBody CoverLetterRequest request) {
        AiTextResponse response = applicationAiService.generateCoverLetter(request);
        return ResponseEntity.ok(ApiResponse.success("Cover letter generated", response));
    }

    @PostMapping("/screening-score")
    public ResponseEntity<ApiResponse<ScreeningScoreResponse>> scoreCandidate(
            @RequestBody ScreeningScoreRequest request) {
        ScreeningScoreResponse response = applicationAiService.scoreCandidate(request);
        return ResponseEntity.ok(ApiResponse.success("Candidate screened", response));
    }




    @PostMapping("/skills-gap")
    public ResponseEntity<ApiResponse<SkillsGapResponse>> analyzeSkillsGap(
            @Valid @RequestBody SkillsGapRequest request) {
        SkillsGapResponse response = applicationAiService.analyzeSkillsGap(request);
        return ResponseEntity.ok(ApiResponse.success("Skills gap analyzed", response));
    }


    @PostMapping("/summarize-notes")
    public ResponseEntity<ApiResponse<AiTextResponse>> summarizeNotes(
            @RequestBody List<String> notes) {
        AiTextResponse response = applicationAiService.summarizeApplicationNotes(notes);
        return ResponseEntity.ok(ApiResponse.success("Notes summarized", response));
    }


}
