package com.project.referral.service.impl;

import com.project.referral.Mapper.JobCategoryMapper;
import com.project.referral.Mapper.JobMapper;
import com.project.referral.common.dto.response.JobCategoryResponse;
import com.project.referral.dto.JobCategoryRequest;
import com.project.referral.modal.JobCategory;
import com.project.referral.repository.JobCategoryRepository;
import com.project.referral.service.JobCategorySevice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobCategoryServiceImpl  implements JobCategorySevice {

    private final JobCategoryRepository jobCategoryRepository;

    @Override
    public JobCategoryResponse createCategory(JobCategoryRequest req) throws Exception {

        if(jobCategoryRepository.existsByName(req.getName())){
            new Exception("Category name already exist,choose different name");
     }
     JobCategory parent= null;
        if(req.getParentId()!=null){
            parent=getCategoryEntityById(req.getParentId());
        }
        String  slug = generateUniqueSlug(req.getName());

        JobCategory category = JobCategory.builder()
                .name(req.getName())
                .slug(slug)
                .description(req.getDescription())
                .iconUrl(req.getIconUrl())
                .parent(parent)
                .active(true)
                .build();

        JobCategory saved =jobCategoryRepository.save(category);
     return JobCategoryMapper.toJobCategoryResponse(saved,true);
    }



    @Override
    public List<JobCategoryResponse> getAllCategories() {
        return jobCategoryRepository.findByActiveTrue().stream()
                .map(c->JobCategoryMapper.toJobCategoryResponse(c,false))
                .collect(Collectors.toList());
    }


    @Override
    public JobCategoryResponse getCategoryById(Long id) {
        JobCategory  jobCategory = getCategoryEntityById(id);
        return JobCategoryMapper.toJobCategoryResponse(jobCategory,true);

    }

    // ── Update ────────────────────────────────────────────────────────────────

    @Override
    public JobCategoryResponse updateCategory(Long id, JobCategoryRequest req)throws Exception {
        JobCategory category = getCategoryEntityById(id);

        if (!category.getName().equals(req.getName())
                && jobCategoryRepository.existsByName(req.getName())) {
            throw new Exception("Category with name '" + req.getName() + "' already exists");
        }
        JobCategory parent = null;

        if (req.getParentId() != null) {
            if (req.getParentId().equals(id)) {
                throw new Exception("A Category can not be its own parent");
            }
            parent = getCategoryEntityById(req.getParentId());
        }
        category.setName(req.getName());
        category.setDescription(req.getDescription());
        category.setIconUrl(req.getIconUrl());
        category.setParent(parent);

        JobCategory updated = jobCategoryRepository.save(category);
        return JobCategoryMapper.toJobCategoryResponse(updated,true);


    }

    @Override
    public void deleteCategory(Long id) {
        JobCategory category = getCategoryEntityById(id);
        category.setActive(false);
        jobCategoryRepository.save(category);


    }

    @Override
    public JobCategory getCategoryEntityById(Long id) {
        return jobCategoryRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Category not Found")
        );
    }


    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("[\\s-]+", "-");
        if (!jobCategoryRepository.existsBySlug(base)) {
            return base;
        }
        int counter = 1;
        while (jobCategoryRepository.existsBySlug(base + "-" + counter)) {
            counter++;
        }
        return base + "-" + counter;
    }
}

