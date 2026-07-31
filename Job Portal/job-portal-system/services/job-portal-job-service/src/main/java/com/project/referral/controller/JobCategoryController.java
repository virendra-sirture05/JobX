package com.project.referral.controller;


import com.project.referral.common.dto.response.ApiResponse;
import com.project.referral.common.dto.response.JobCategoryResponse;
import com.project.referral.dto.JobCategoryRequest;
import com.project.referral.service.JobCategorySevice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-categories")
@RequiredArgsConstructor
public class JobCategoryController {

    private final JobCategorySevice jobcategoryService;

    @PostMapping
    public ResponseEntity<JobCategoryResponse> createCategory(
            @RequestBody @Valid JobCategoryRequest req)
            throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobcategoryService.createCategory(req));
    }

   /* @PostMapping("/bulk")
    public ResponseEntity<BulkJobCategoryResponse> createCategoriesBulk(
            @RequestBody @Valid BulkJobCategoryRequest req) {
        return ResponseEntity.status(HttpStatus.MULTI_STATUS)
                .body(jobcategoryService.createCategoriesBulk(req));
    }

    */

    @GetMapping
    public ResponseEntity<List<JobCategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(jobcategoryService.getAllCategories());
    }

/*    @GetMapping("/root")
    public ResponseEntity<List<JobCategoryResponse>> getRootCategories() {
        return ResponseEntity.ok(jobcategoryService.getRootCategories());
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<JobCategoryResponse> getCategoryById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(jobcategoryService.getCategoryById(id));
    }

    /*@GetMapping("/slug/{slug}")
    public ResponseEntity<JobCategoryResponse> getCategoryBySlug(
            @PathVariable String slug) throws Exception {
        return ResponseEntity.ok(jobcategoryService.getCategoryBySlug(slug));
    }

     */

    @PutMapping("/{id}")
    public ResponseEntity<JobCategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid JobCategoryRequest req)
            throws Exception {
        return ResponseEntity.ok(jobcategoryService.updateCategory(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(
            @PathVariable Long id) throws Exception {
        jobcategoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse("Category deleted successfully", true));
    }
}



