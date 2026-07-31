package com.project.referral.service;

import com.project.referral.common.dto.response.JobTagResponse;
import com.project.referral.dto.JobTagRequest;
import com.project.referral.modal.JobTag;

import java.util.List;
import java.util.Set;

public interface JobTagService {

    JobTagResponse createTag(JobTagRequest req) throws Exception;
    List<JobTagResponse> getAllTags();
    JobTagResponse getById(Long id);
    JobTagResponse updateTag(Long id,JobTagRequest req) throws Exception;
    void deleteTag(Long id) throws Exception;
    JobTag getTagEntityById (Long id) throws Exception;
    Set<JobTag>getTagByIds(Set<Long>ids) throws Exception;
}
