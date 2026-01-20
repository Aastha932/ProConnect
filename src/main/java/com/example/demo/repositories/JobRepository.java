package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, Long>{
	//to  solve n+1 problem
	@Query("SELECT j FROM JobEntity j JOIN FETCH j.owner")
    List<JobEntity> findAllWithOwners();

	    List<JobEntity> findByBudgetGreaterThan(Double amount);

	    //  Custom JPQL Query
	    @Query("SELECT j FROM JobEntity j WHERE j.title LIKE %:keyword%")
	    List<JobEntity> searchByTitle(String keyword);
	    
	    //  N+1 Problem solve
	    @Query("SELECT j FROM JobEntity j JOIN FETCH j.owner WHERE j.budget > :amount")
	    List<JobEntity> findHighPayingJobsWithOwners(Double amount);
	}


