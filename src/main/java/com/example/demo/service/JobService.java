package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.JobDTO;
import com.example.demo.entities.JobEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.JobRepository;
import com.example.demo.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class JobService {
	@Autowired
    private JobRepository jobRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired private UserRepository userRepository;

    @Transactional
    public JobDTO createJob(Long userId, JobDTO jobDto) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        JobEntity jobEntity = modelMapper.map(jobDto, JobEntity.class);
        jobEntity.setOwner(user); // Relationship mapping

        JobEntity savedJob = jobRepository.save(jobEntity);
        return modelMapper.map(savedJob, JobDTO.class);
    }

    public List<JobDTO> findAllJobs() {
        return jobRepository.findAll().stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    public JobDTO findJobById(Long id) {
        JobEntity job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        return modelMapper.map(job, JobDTO.class);
    }

    public void removeJob(Long id) {
        if(!jobRepository.existsById(id)) throw new ResourceNotFoundException("Invalid ID");
        jobRepository.deleteById(id);
    }
    
 // Pagination
    public Page<JobDTO> findAllJobsSortedAndPaginated(int pageNumber, int pageSize, String sortBy) {
        
        org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        Page<JobEntity> jobPage = jobRepository.findAll(pageable);

        return jobPage.map(job -> modelMapper.map(job, JobDTO.class));
    }
    
 //  High Budget Jobs  (Finder Method use karega)
    public List<JobDTO> getHighBudgetJobs(Double minBudget) {
        List<JobEntity> jobs = jobRepository.findByBudgetGreaterThan(minBudget);
        return jobs.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    //  Title Search (Custom @Query use karega)
    public List<JobDTO> searchJobs(String keyword) {
        List<JobEntity> jobs = jobRepository.searchByTitle(keyword);
        return jobs.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    // Performance Optimized Search (N+1 fix )
   
    public List<JobDTO> getHighPayingJobsWithOwners(Double amount) {
        List<JobEntity> jobs = jobRepository.findHighPayingJobsWithOwners(amount);
        return jobs.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }
    
  
	

}
