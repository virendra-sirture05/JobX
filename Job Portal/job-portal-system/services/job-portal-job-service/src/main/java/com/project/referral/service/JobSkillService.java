package com.project.referral.service;

import com.project.referral.common.domain.SkillCategory;
import com.project.referral.common.dto.response.JobSkillResponse;
import com.project.referral.dto.JobSkillRequest;
import com.project.referral.modal.JobSkill;

import java.util.List;
import java.util.Set;

public interface JobSkillService {

    JobSkillResponse createSkill(JobSkillRequest req) throws Exception;
    //BulkJobSkillResponse createSkillsBulk(BulkJobSkillRequest req);

    List<JobSkillResponse> getAllSkills();

    List<JobSkillResponse> getSkillsByCategory(SkillCategory category);

    List<JobSkillResponse> searchSkills(String keyword);

    JobSkillResponse getSkillById(Long id) throws Exception;

    JobSkillResponse updateSkill(Long id, JobSkillRequest req)
            throws Exception;

    void deleteSkill(Long id) throws Exception;

    /** Used internally to load skills by IDs for job creation. */
    Set<JobSkill> getSkillEntitiesByIds(Set<Long> ids) throws Exception;

    Set<JobSkill> getSkillByIds(Set<Long> ids);
}

