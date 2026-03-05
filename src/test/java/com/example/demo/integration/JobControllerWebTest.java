package com.example.demo.integration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.demo.ProConnectApplication;
import com.example.demo.dto.JobDTO;
import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = ProConnectApplication.class)
@AutoConfigureMockMvc
public class JobControllerWebTest {

@Autowired
private MockMvc mockMvc;

@Autowired
private ObjectMapper objectMapper;
@Autowired
private UserRepository userRepository;

@Test
@WithMockUser(username = "admin", roles = {"ADMIN"})
void testGetAllJobs() throws Exception {

    mockMvc.perform(get("/api/v1/users/jobs"))
            .andExpect(status().isOk());
}

@Test
@WithMockUser(username = "admin", roles = {"ADMIN"})
void testCreateJob() throws Exception {

    UserEntity user = new UserEntity();
    user.setName("Admin");
    user = userRepository.save(user);

    JobDTO jobDTO = new JobDTO();
    jobDTO.setTitle("Cloud Engineer");
    jobDTO.setBudget(80000.0);

    mockMvc.perform(
            post("/api/v1/users/" + user.getId() + "/jobs")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(jobDTO))
    )
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.title").value("Cloud Engineer"));
}


}
