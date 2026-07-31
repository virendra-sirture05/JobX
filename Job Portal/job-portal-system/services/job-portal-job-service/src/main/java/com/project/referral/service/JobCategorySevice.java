package com.project.referral.service;

import com.project.referral.common.dto.response.JobCategoryResponse;
import com.project.referral.dto.JobCategoryRequest;
import com.project.referral.modal.JobCategory;

import java.util.List;

public interface JobCategorySevice {
    JobCategoryResponse createCategory(JobCategoryRequest req)   throws Exception;

    List<JobCategoryResponse> getAllCategories();

   // List<JobCategoryResponse> getRootCategories();

    JobCategoryResponse getCategoryById(Long id);

    JobCategoryResponse updateCategory(Long id, JobCategoryRequest req) throws Exception;

    void deleteCategory(Long id);

    JobCategory getCategoryEntityById(Long id);

}
