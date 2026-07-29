package com.project.referral.repository;

import com.project.referral.common.domain.SkillCategory;
import com.project.referral.modal.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface JobSkillRepository extends JpaRepository<JobSkill,Long> {

    Optional<JobSkill> findBySlug(String slug);

    List<JobSkill> findByActiveTrue();

    List<JobSkill> findByCategoryAndActiveTrue(SkillCategory category);

    List<JobSkill> findByNameContainingIgnoreCaseAndActiveTrue(String keyword);

    boolean existsByName(String name);

    boolean existsBySlug(String slug);
}


