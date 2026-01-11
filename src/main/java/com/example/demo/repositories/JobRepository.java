package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, Long>{

}
