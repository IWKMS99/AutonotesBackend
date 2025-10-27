package ru.mtuci.autonotesbackend.app.web.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mtuci.autonotesbackend.modules.user.api.UserApi;
import ru.mtuci.autonotesbackend.modules.user.api.dto.AuthRequestDto;
import ru.mtuci.autonotesbackend.modules.user.api.dto.AuthResponseDto;
import ru.mtuci.autonotesbackend.modules.user.api.dto.RegistrationRequestDto;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthResource {

    private final UserApi userApi;

    @Override
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegistrationRequestDto request) {
        AuthResponseDto response = userApi.register(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/users/{username}")
                .buildAndExpand(request.getUsername()).toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(userApi.login(request));
    }
}