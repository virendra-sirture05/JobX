package com.zosh.job.service;

import com.zosh.job.common.dto.response.SavedJobResponse;
import com.zosh.job.dto.request.SavedJobRequest;
import com.zosh.job.mapper.PreferenceMapper;
import com.zosh.job.model.SavedJob;
import com.zosh.job.repository.SavedJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedJobServiceImpl implements SavedJobService {

    private final SavedJobRepository savedJobRepository;


    @Override
    public SavedJobResponse saveJob(Long candidateId, SavedJobRequest req) throws Exception {
        if(isSaved(candidateId, req.getJobId())){
            throw new Exception("Job already saved");
        }
        SavedJob savedJob = SavedJob.builder()
                .candidateId(candidateId)
                .jobId(req.getJobId())
                .build();

        savedJob = savedJobRepository.save(savedJob);
        return PreferenceMapper.toSavedJobResponse(savedJob);
    }

    @Override
    public void unsaveJob(Long candidateId, Long savedJobId) throws Exception {
        SavedJob savedJob = savedJobRepository.findById(savedJobId).orElseThrow(
                ()-> new Exception("job not found")
        );
        if(!savedJob.getCandidateId().equals(candidateId)){
            throw new Exception("job not saved");
        }
        savedJobRepository.delete(savedJob);
    }

    @Override
    public List<SavedJobResponse> getSavedJob(Long candidateId) {
        return savedJobRepository.findByCandidateId(candidateId)
                .stream().map(PreferenceMapper::toSavedJobResponse).toList();
    }

    @Override
    public boolean isSaved(Long candidateId, Long jobId) {
        return savedJobRepository.existsByCandidateIdAndJobId(candidateId, jobId);
    }
}
