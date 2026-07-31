package com.project.referral.Mapper;

import com.project.referral.common.dto.response.JobSkillResponse;
import com.project.referral.modal.JobSkill;

public class JobSkillMapper {

public static JobSkillResponse toJobSkillResponse(JobSkill skill){
    return JobSkillResponse.builder()
            .id(skill.getId())
            .name(skill.getName())
            .slug(skill.getSlug())
            .category(skill.getCategory())
            .active(skill.getActive())
            .build();

}
}
