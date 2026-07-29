package com.project.referral.modal;

import com.project.referral.common.dto.response.JobTagResponse;

public class JobTagMapper {
    public static JobTagResponse toTagResponse(JobTag tag) {
        return JobTagResponse.builder()
                .id(tag.getId())
                .name(tag.getName())
                .slug(tag.getSlug())
                .build();
    }

}
