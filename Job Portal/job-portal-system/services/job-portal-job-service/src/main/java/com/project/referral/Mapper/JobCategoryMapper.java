package com.project.referral.Mapper;

import com.project.referral.common.dto.response.JobCategoryResponse;
import com.project.referral.modal.JobCategory;
import jdk.jfr.Category;

import java.util.List;
import java.util.stream.Collectors;

public class JobCategoryMapper {

    public static JobCategoryResponse toJobCategoryResponse (JobCategory category ,boolean includeChildren){

        List<JobCategoryResponse> subCategories = null;
              if(includeChildren && category.getSubCategories()!=null) {
                   subCategories =category.getSubCategories()
                          .stream().map(sub -> toJobCategoryResponse(sub, false))
                          .collect(Collectors.toList());
              }


      return JobCategoryResponse.builder()
              .id(category.getId())
              .name(category.getName())
              .slug(category.getSlug())
              .description(category.getDescription())
              .iconUrl(category.getIconUrl())
              .active(category.getActive())
              .parentId(category.getId())
              .subCategories(subCategories)
              .createdAt(category.getCreatedAt())
              .createdAt(category.getCreatedAt())
              .build();
    }
}
