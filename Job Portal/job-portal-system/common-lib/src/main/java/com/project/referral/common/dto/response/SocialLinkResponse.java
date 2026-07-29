package com.project.referral.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.referral.common.domain.SocialPlatform;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocialLinkResponse {

    private SocialPlatform platform;
    private String url;
}
