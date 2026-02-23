package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.JobDTO;
import com.example.demo.service.JobService;

@RestController
@RequestMapping("/api/v1/users")
public class JobController {
	@Autowired
    private JobService jobService;
	// 1. CREATE JOB FOR A USER (URL: POST /api/v1/users/{userId}/jobs)
    @PostMapping("/{userId}/jobs")
    @PreAuthorize("hasRole('ADMIN')")
    
    public ResponseEntity<JobDTO> createJobForUser(@PathVariable Long userId, @RequestBody JobDTO jobDto) {
        return ResponseEntity.ok(jobService.createJob(userId, jobDto));
    }

    // 2. GET ALL JOBS (URL: GET /api/v1/users/jobs)
    @GetMapping("/jobs")
    public List<JobDTO> getAllJobs() {
        return jobService.findAllJobs();
    }

    // 3. GET JOB BY ID (URL: GET /api/v1/users/jobs/{id})
    @GetMapping("/jobs/{id}")
    public JobDTO getJobById(@PathVariable Long id) {
        return jobService.findJobById(id);
    }
	
    // 4. DELETE JOB (URL: DELETE /api/v1/users/jobs/{id})
    @DeleteMapping("/jobs/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteJob(@PathVariable Long id) {
        jobService.removeJob(id);
        return "Job deleted successfully with ID: " + id;
    }
    
    //5.Pagination and sorting
 // GET http://localhost:8080/api/v1/users/jobs/paginated?pageNumber=0&pageSize=5&sortBy=budget
    @GetMapping("/jobs/paginated")
    @PreAuthorize("hasAnyRole('ADMIN', 'CANDIDATE')")
    public ResponseEntity<Page<JobDTO>> getAllJobsPaginated(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(jobService.findAllJobsSortedAndPaginated(pageNumber, pageSize, sortBy));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<JobDTO>> searchJobs(
            @RequestParam(required = false) Double minBudget,
            @RequestParam(required = false) String title) {
        
        if (minBudget != null) {
            return ResponseEntity.ok(jobService.getHighBudgetJobs(minBudget));
        } else if (title != null) {
            return ResponseEntity.ok(jobService.searchJobs(title));
        }
        return ResponseEntity.ok(jobService.findAllJobs());
    }
    
 
}

    



