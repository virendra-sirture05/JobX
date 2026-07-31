package com.project.referral.controller;


import com.project.referral.common.dto.response.ApiResponse;
import com.project.referral.common.dto.response.JobTagResponse;
import com.project.referral.dto.JobTagRequest;
import com.project.referral.service.JobTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-tags")
@RequiredArgsConstructor
public class JobTagController {
    private final JobTagService tagService;

    @PostMapping
    public ResponseEntity<JobTagResponse> createTag(
            @RequestBody @Valid JobTagRequest req) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagService.createTag(req));
    }

    @GetMapping
    public ResponseEntity<List<JobTagResponse>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }


    @GetMapping("/{id}")
    public ResponseEntity<JobTagResponse> getTagById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(tagService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobTagResponse> updateTag(
            @PathVariable Long id,
            @RequestBody @Valid JobTagRequest req)
            throws Exception {
        return ResponseEntity.ok(tagService.updateTag(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse>deleteTag(
            @PathVariable Long id) throws Exception{
        tagService.deleteTag(id);
        return ResponseEntity.ok(new ApiResponse("Tag deleted successfully",true));
    }

}
