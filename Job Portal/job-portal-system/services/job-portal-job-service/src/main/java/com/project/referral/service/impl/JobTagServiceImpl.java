package com.project.referral.service.impl;

import com.project.referral.common.dto.response.JobTagResponse;
import com.project.referral.dto.JobTagRequest;
import com.project.referral.modal.JobTag;
import com.project.referral.modal.JobTagMapper;
import com.project.referral.repository.JobTagRepository;
import com.project.referral.service.JobTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobTagServiceImpl implements JobTagService {
    private final JobTagRepository jobTagRepository;

    @Override
    @Transactional
    public JobTagResponse createTag(JobTagRequest req) throws Exception {
        if (jobTagRepository.existsByName(req.getName())) {
            throw new Exception("Tag '" + req.getName() + "' already exists");
        }
        String slug = generateUniqueSlug(req.getName());

        JobTag tag = JobTag.builder()
                .name(req.getName())
                .slug(slug)
                .build();
      JobTag saved = jobTagRepository.save(tag);
        return JobTagMapper.toTagResponse(jobTagRepository.save(tag));
    }


    @Override
    public List<JobTagResponse> getAllTags() {
       return jobTagRepository.findAll()
               .stream().map(JobTagMapper::toTagResponse)
               .collect(Collectors.toList());
    }

    @Override
    public JobTagResponse getById(Long id) {
        JobTag jobTag =jobTagRepository.findById(id).orElse(null);
        return JobTagMapper.toTagResponse(jobTag);
    }

    @Override
    @Transactional
    public JobTagResponse updateTag(Long id, JobTagRequest req) throws Exception {
        JobTag tag = getTagEntityById(id);

        if (!tag.getName().equals(req.getName())
                && jobTagRepository.existsByName(req.getName())) {
            throw new Exception("Tag '" + req.getName() + "' already exists");
        }

        tag.setName(req.getName());
        return JobTagMapper.toTagResponse(jobTagRepository.save(tag));
    }


    @Override
    public void deleteTag(Long id) throws Exception {
      JobTag jobTag = getTagEntityById(id);
      jobTagRepository.delete(jobTag);

    }

    @Override
    public JobTag getTagEntityById(Long id) throws Exception {
        return jobTagRepository.findById(id).orElseThrow(
                ()->new Exception("Job tag not found")
        );

    }

    @Override
    public Set<JobTag> getTagByIds(Set<Long> ids) throws Exception {
        List<JobTag>  tags=jobTagRepository.findAllById(ids);
        return  new HashSet<>(tags);
    }

    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("[\\s-]+", "-");
        if (!jobTagRepository.existsBySlug(base)) {
            return base;
        }
        int counter = 1;
        while (jobTagRepository.existsBySlug(base + "-" + counter)) {
            counter++;
        }
        return base + "-" + counter;
    }
}
