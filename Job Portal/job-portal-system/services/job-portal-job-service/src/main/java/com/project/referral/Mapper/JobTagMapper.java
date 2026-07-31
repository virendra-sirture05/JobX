package com.project.referral.Mapper;

import com.project.referral.common.dto.response.JobTagResponse;
import com.project.referral.modal.JobTag;

public class JobTagMapper {

    public static JobTagResponse toTagResponse(JobTag tag) {
        return JobTagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .slug(tag.getSlug())
                .build();
    }
}
