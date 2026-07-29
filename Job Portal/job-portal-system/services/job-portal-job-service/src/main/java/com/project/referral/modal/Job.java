package com.project.referral.modal;


import com.project.referral.common.domain.ExperienceLevel;
import com.project.referral.common.domain.JobStatus;
import com.project.referral.common.domain.JobType;
import com.project.referral.common.domain.WorkMode;
import com.project.referral.modal.embeddable.JobLocation;
import com.project.referral.modal.embeddable.SalaryRange;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    @Column(columnDefinition = "TEXT")
    private String responsibilities;

    @Column(columnDefinition = "TEXT")
    private String benefits;

    /** ID of the company that posted this job (from company-service). */
    @Column(nullable = false)
    private Long companyId;

    /** ID of the employer/user who created this posting (from user-service). */
    @Column(nullable = false)
    private Long employerId;

    @ManyToOne
    private  JobCategory category;

    @ManyToMany
    private Set<JobSkill> skills;

    @ManyToMany
    private Set<JobTag>tags;

    @Embedded
    private JobLocation location;

    @Embedded
    private SalaryRange salaryRange;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType jobType;

    @Column(nullable = false)
    private WorkMode workMode;

    /** Seniority expected from the candidate. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExperienceLevel experienceLevel;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private JobStatus status = JobStatus.DRAFT;

    // -------------------------------------------------------
    // Posting details
    // -------------------------------------------------------
    @Column(nullable = false)
    @Builder.Default
    private Integer openings = 1;

    /** Last date candidates can apply. */
    private LocalDate applicationDeadline;

    /** When the posting should auto-expire (system enforced). */
    private LocalDate expiresAt;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;



    // -------------------------------------------------------
    // Timestamps
    // -------------------------------------------------------

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime publishedAt;
    private LocalDateTime closedAt;

}
