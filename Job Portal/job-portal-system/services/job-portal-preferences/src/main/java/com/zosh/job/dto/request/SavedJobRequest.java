package com.zosh.job.dto.request;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SavedJobRequest {
    private Long jobId;

    private Long companyId;
    private String notes;

    public Long getJobId() {
        return  jobId;
    }
}
