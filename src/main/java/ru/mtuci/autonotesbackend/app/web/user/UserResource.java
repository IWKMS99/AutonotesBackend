package ru.mtuci.autonotesbackend.app.web.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import ru.mtuci.autonotesbackend.modules.user.api.dto.UserProfileDto;

@Tag(name = "02. Пользователи", description = "API для управления профилями пользователей")
@SecurityRequirement(name = "bearerAuth")
public interface UserResource {

    @Operation(summary = "Получить профиль пользователя по имени")
    ResponseEntity<UserProfileDto> getProfile(@PathVariable String username);
}