package com.project.referral.service.impl;

import com.project.referral.Mapper.JobSkillMapper;
import com.project.referral.common.domain.SkillCategory;
import com.project.referral.common.dto.response.JobSkillResponse;
import com.project.referral.dto.JobSkillRequest;
import com.project.referral.modal.JobSkill;
import com.project.referral.repository.JobSkillRepository;
import com.project.referral.service.JobSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobSkillServiceImpl  implements JobSkillService {
    private final JobSkillRepository jobSkillRepository;

    @Override
    public JobSkillResponse createSkill(JobSkillRequest req) throws Exception {
        if (jobSkillRepository.existsByName(req.getName())) {
            throw new Exception("skill name already exist");
        }
        String slug = generateUniqueSlug(req.getName());

        JobSkill skill = JobSkill.builder()
                .name(req.getName())
                .slug(slug)
                .category(req.getCategory())
                .build();
         JobSkill savedSkill = jobSkillRepository.save(skill);
        return JobSkillMapper.toJobSkillResponse(savedSkill);

    }

    @Override
    public List<JobSkillResponse> getAllSkills() {
        return jobSkillRepository.findByActiveTrue()
                .stream().map(JobSkillMapper::toJobSkillResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobSkillResponse> getSkillsByCategory(SkillCategory category) {
        return List.of();
    }

    @Override
    public List<JobSkillResponse> searchSkills(String keyword) {
        return List.of();
    }

    @Override
    public JobSkillResponse getSkillById(Long id) throws Exception {
        JobSkill skill = jobSkillRepository.findById(id).orElseThrow(
                ()->new Exception("job skill not found")
        );
        return JobSkillMapper.toJobSkillResponse(skill);
    }

    @Override
    public JobSkillResponse updateSkill(Long id, JobSkillRequest req) throws Exception {
       JobSkill skill = jobSkillRepository.findById(id).orElseThrow(
               ()->new Exception("Job skill not found")
       );
       if(!skill.getName().equals(req.getName())
           && jobSkillRepository.existsByName(skill.getName())){
           throw new Exception("skill name already exist");
       }
       skill.setName(req.getName());
       skill.setCategory(req.getCategory());
       JobSkill updated = jobSkillRepository.save(skill);

       return JobSkillMapper.toJobSkillResponse(updated);
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public void deleteSkill(Long id) throws Exception {
        JobSkill skill = jobSkillRepository.findById(id).orElseThrow(
                ()->new Exception("Job skill not found")
        );
        skill.setActive(false);
        jobSkillRepository.save(skill);
    }
    @Override
    public Set<JobSkill> getSkillEntitiesByIds(Set<Long> ids) throws Exception {
        return Set.of();
    }
    @Override
    public Set<JobSkill> getSkillByIds(Set<Long> ids) {
        List<JobSkill> skills = jobSkillRepository.findAllById(ids);
        return new HashSet<>(skills);
    }


    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("[\\s-]+", "-");
        if (!jobSkillRepository.existsBySlug(base)) {
            return base;
        }
        int counter = 1;
        while (jobSkillRepository.existsBySlug(base + "-" + counter)) {
            counter++;
        }
        return base + "-" + counter;
    }

}