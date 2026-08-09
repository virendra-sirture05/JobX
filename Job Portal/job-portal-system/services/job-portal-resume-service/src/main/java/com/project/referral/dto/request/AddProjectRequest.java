package com.project.referral.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AddProjectRequest {
    @NotBlank(message = "Project title is required") private String title;
    private String description;
    private List<String> technologies;
    private String projectUrl;
    private String sourceCodeUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    @Builder.Default private Boolean isOngoing = false;
    private Integer displayOrder;
}
