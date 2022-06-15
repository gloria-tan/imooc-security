package com.billwen.learning.imooc.uaa.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestPropertySource(locations = {"classpath:.env.test"})
@ActiveProfiles("test")
@SpringBootTest
public class SecuredRestAPIIntTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @WithMockUser
    @Test
    public void givenAuthRequest_shouldSuccessWith200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/greeting"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithMockUser(username = "zhangsan", roles = {"ADMIN"})
    @Test
    public void givenRoleUserOrAdmin_thenAccessSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{username}", "user"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithMockUser(username = "superman", roles = {"ADMIN", "USER"})
    @Test
    public void givenUserRole_whenQueryUserByEmail_shouldSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/by-email/{email}", "san.zhang@local.dev"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithMockUser(username = "zhangsan", roles = {"ADMIN"})
    @Test
    public void givenRoleManagerOrAdmin_thenAccessSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/users/manager/{message}", "world"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
