package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.JobDTO;
import com.example.demo.service.JobService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
	@Autowired
    private JobService jobService;

    // 1. Create Job
    @PostMapping
    public JobDTO createJob(@Valid @RequestBody JobDTO jobDto) {
        return jobService.postNewJob(jobDto);
    }

    // 2. Get All Jobs
    @GetMapping
    public List<JobDTO> getAllJobs() {
        return jobService.findAllJobs();
    }

    // 3. Get Job by ID
    @GetMapping("/{id}")
    public JobDTO getJobById(@PathVariable Long id) {
        return jobService.findJobById(id);
    }

    // 4. Update Job (PUT)
    @PutMapping("/{id}")
    public JobDTO updateJob(@PathVariable Long id, @Valid @RequestBody JobDTO jobDto) {
        return jobService.updateJob(id, jobDto);
    }

    // 5. Partial Update (PATCH)
    @PatchMapping("/{id}")
    public JobDTO patchJob(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return jobService.patchJob(id, updates);
    }

    // 6. Delete Job
    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.removeJob(id);
        return "Job deleted successfully with ID: " + id;
    }
}


