package ru.mtuci.autonotesbackend.app.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;
import ru.mtuci.autonotesbackend.BaseIntegrationTest;
import ru.mtuci.autonotesbackend.modules.user.api.dto.AuthRequestDto;
import ru.mtuci.autonotesbackend.modules.user.api.dto.AuthResponseDto;
import ru.mtuci.autonotesbackend.modules.user.impl.domain.User;
import ru.mtuci.autonotesbackend.modules.user.impl.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void getProfile_whenAuthenticated_shouldReturnOwnProfile() throws Exception {
        // Arrange
        createUserInDb("testuser", "test@mail.com", "password123");
        String token = loginAndGetToken("testuser", "password123");

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/testuser")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@mail.com"));
    }

    @Test
    void getProfile_whenRequestingOtherUsersProfile_shouldReturnForbidden() throws Exception {
        // Arrange
        createUserInDb("user1", "user1@mail.com", "password123");
        createUserInDb("user2", "user2@mail.com", "password123");
        String tokenUser1 = loginAndGetToken("user1", "password123");

        // Act & Assert
        mockMvc.perform(get("/api/v1/users/user2")
                        .header("Authorization", "Bearer " + tokenUser1))
                .andExpect(status().isForbidden());
    }

    private void createUserInDb(String username, String email, String rawPassword) {
        userRepository.save(User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(rawPassword))
                .build());
    }

    private String loginAndGetToken(String username, String password) throws Exception {
        AuthRequestDto authRequest = new AuthRequestDto(username, password);
        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        return objectMapper.readValue(responseBody, AuthResponseDto.class).getToken();
    }
}