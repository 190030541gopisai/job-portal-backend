package com.gopi.job.service.impl;

import com.gopi.job.dto.JobSkillResponse;
import com.gopi.job.mapper.JobSkillMapper;
import com.gopi.job.modal.JobSkill;
import com.gopi.job.payload.JobSkillRequest;
import com.gopi.job.repository.JobSkillRepository;
import com.gopi.job.service.JobSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobSkillServiceImpl implements JobSkillService {
    private final JobSkillRepository jobSkillRepository;

    @Override
    public JobSkillResponse createSkill(JobSkillRequest req) throws Exception{
        if(jobSkillRepository.existsByName(req.getName())){
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
                .stream().map(
                        JobSkillMapper::toJobSkillResponse
                ).collect(Collectors.toList());
    }

    @Override
    public JobSkillResponse getSkillById(Long id) throws Exception{
        JobSkill jobSkill = jobSkillRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("job skill not found")
                );

        return JobSkillMapper.toJobSkillResponse(jobSkill);
    }

    @Override
    public JobSkillResponse updateSkill(Long id, JobSkillRequest req) throws Exception {

        JobSkill jobSkill = jobSkillRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("job skill not found")
                );

        if(!jobSkill.getName().equals(req.getName())
            && jobSkillRepository.existsByName(jobSkill.getName())
        ) {
            throw new Exception("skill name already exists");
        }

        jobSkill.setName(req.getName());
        jobSkill.setCategory(req.getCategory());

        return JobSkillMapper.toJobSkillResponse(jobSkillRepository.save(jobSkill));
    }

    @Override
    public void deleteSkill(Long id) throws Exception {
        JobSkill jobSkill = jobSkillRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("job skill not found")
                );

        jobSkill.setActive(false);

        jobSkillRepository.save(jobSkill);
    }

    @Override
    public Set<JobSkill> getSkillsByIds(Set<Long> ids) {
        return new HashSet<>(jobSkillRepository.findAllById(ids));
    }

    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim().replaceAll("[\\s-]+", "-");

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
