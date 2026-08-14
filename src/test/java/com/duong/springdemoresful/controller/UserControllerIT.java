package com.duong.springdemoresful.controller;

import com.duong.springdemoresful.dto.request.UserRequestCreate;
import com.duong.springdemoresful.model.Role;
import com.duong.springdemoresful.repository.RoleRepository;
import com.duong.springdemoresful.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RoleRepository roleRepository;

    private Role testRole;

    @BeforeEach
    public void setUp() {
        testRole = roleRepository.findByIdOrName(null, "ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ADMIN");
                    role.setDescription("Quyền ADMIN");
                    return roleRepository.save(role);
                });
    }

    @Test
    public void createUser_shouldReturnUser_whenValid() throws Exception {
        Role roleForRequest = new Role(testRole.getId(), testRole.getName(), testRole.getDescription(), null);
        String uniqueEmail = "test_" + System.currentTimeMillis() + "@gmail.com";
        UserRequestCreate userRequestCreate = new UserRequestCreate(null, "Duong", uniqueEmail, "123456", "Hanoi", roleForRequest);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequestCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.name").value("Duong"))
                .andExpect(jsonPath("$.data.email").value(uniqueEmail));
    }
}
