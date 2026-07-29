package com.project.referral.client;


import com.project.referral.common.dto.response.ApiResponse;
import com.project.referral.dto.request.ScreeningScoreRequest;
import com.project.referral.dto.response.ScreeningScoreResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "job-portal-ai-service",
//        url = "${job.service.url:http://localhost:8082}")
//TODO ADD WORKING SERVICES
public interface AiClient {

    //@PostMapping("/api/ai/application/screening-score")
    ApiResponse<ScreeningScoreResponse> scoreCandidate(@RequestBody ScreeningScoreRequest request);
}
