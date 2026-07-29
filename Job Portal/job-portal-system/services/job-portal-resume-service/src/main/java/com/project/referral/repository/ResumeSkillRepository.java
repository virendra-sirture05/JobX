package com.project.referral.repository;

import com.project.referral.entity.ResumeSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeSkillRepository extends JpaRepository<ResumeSkill, Long> {

    List<ResumeSkill> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);
}
