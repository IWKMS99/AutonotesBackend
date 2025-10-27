package ru.mtuci.autonotesbackend.modules.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mtuci.autonotesbackend.modules.user.api.UserApi;
import ru.mtuci.autonotesbackend.modules.user.api.dto.AuthRequestDto;
import ru.mtuci.autonotesbackend.modules.user.api.dto.AuthResponseDto;
import ru.mtuci.autonotesbackend.modules.user.api.dto.RegistrationRequestDto;
import ru.mtuci.autonotesbackend.modules.user.impl.service.AuthService;

@Component
@RequiredArgsConstructor
public class UserApiImpl implements UserApi {

    private final AuthService authService;

    @Override
    public AuthResponseDto register(RegistrationRequestDto request) {
        return authService.register(request);
    }

    @Override
    public AuthResponseDto login(AuthRequestDto request) {
        return authService.login(request);
    }
}