package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.example.demo.dto.JobDTO;
import com.example.demo.entities.JobEntity;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.JobRepository;
import com.example.demo.repositories.UserRepository;
@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @Mock
    private JobRepository jobRepository; 

    @Mock
    private ModelMapper modelMapper; 
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JobService jobService; 

	 @Test
	    void testFindAllJobs() {
	        // 1. Arrange (Data setup)
	        JobEntity job = new JobEntity();
	        job.setId(1L);
	        JobDTO jobDto = new JobDTO();
	        jobDto.setId(1L);

	        when(jobRepository.findAll()).thenReturn(List.of(job));
	        when(modelMapper.map(job, JobDTO.class)).thenReturn(jobDto);

	        // 2. Act (Method call)
	        List<JobDTO> result = jobService.findAllJobs();

	        // 3. Assert (Check result)
	        assertThat(result).hasSize(1);
	        assertThat(result.get(0).getId()).isEqualTo(1L);
	    }
	 @Test
	    void testCreateJob_UserNotFound_ThrowsException() {
	        // Arrange
	        Long userId = 99L;
	        when(userRepository.findById(userId)).thenReturn(Optional.empty());

	        // Act & Assert
	        assertThatThrownBy(() -> jobService.createJob(userId, new JobDTO()))
	                .isInstanceOf(ResourceNotFoundException.class);
	        
	        verify(jobRepository,never()).save(any());
	    }

}
