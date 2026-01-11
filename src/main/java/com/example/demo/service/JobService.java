package com.example.demo.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.example.demo.dto.JobDTO;
import com.example.demo.entities.JobEntity;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.JobRepository;

@Service
public class JobService {
	@Autowired
    private JobRepository jobRepository;

    @Autowired
    private ModelMapper modelMapper;

    public JobDTO postNewJob(JobDTO jobDto) {
        JobEntity entity = modelMapper.map(jobDto, JobEntity.class);
        return modelMapper.map(jobRepository.save(entity), JobDTO.class);
    }

    public List<JobDTO> findAllJobs() {
        return jobRepository.findAll().stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    public JobDTO findJobById(Long id) {
        JobEntity job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id)); // Lecture 2.7 concept
        return modelMapper.map(job, JobDTO.class);
    }

    public JobDTO updateJob(Long id, JobDTO jobDto) {
        if(!jobRepository.existsById(id)) throw new ResourceNotFoundException("Job not found");
        JobEntity entity = modelMapper.map(jobDto, JobEntity.class);
        entity.setId(id);
        return modelMapper.map(jobRepository.save(entity), JobDTO.class);
    }

    public JobDTO patchJob(Long id, Map<String, Object> updates) {
        JobEntity jobEntity = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(JobEntity.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, jobEntity, value);
            }
        });
        return modelMapper.map(jobRepository.save(jobEntity), JobDTO.class);
    }

    public void removeJob(Long id) {
        if(!jobRepository.existsById(id)) throw new ResourceNotFoundException("Invalid ID");
        jobRepository.deleteById(id);
    }
	

}
