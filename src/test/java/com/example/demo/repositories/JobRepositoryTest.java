package com.example.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.entities.JobEntity;
import com.example.demo.entities.UserEntity;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class JobRepositoryTest {
	@Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByBudgetGreaterThan_ShouldReturnMatchingJobs() {

        UserEntity user = new UserEntity();
        user.setEmail("test@test.com");
        user.setPassword("password");
        userRepository.save(user);

        JobEntity job1 = new JobEntity();
        job1.setTitle("Java Dev");
        job1.setBudget(50000.0);
        job1.setOwner(user);
        jobRepository.save(job1);

        JobEntity job2 = new JobEntity();
        job2.setTitle("Python Dev");
        job2.setBudget(20000.0);
        job2.setOwner(user);
        jobRepository.save(job2);

        List<JobEntity> result = jobRepository.findByBudgetGreaterThan(30000.0);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Java Dev");
    }
}
