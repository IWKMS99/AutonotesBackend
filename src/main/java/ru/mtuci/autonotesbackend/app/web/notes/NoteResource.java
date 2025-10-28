package ru.mtuci.autonotesbackend.app.web.notes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.mtuci.autonotesbackend.modules.notes.api.dto.NoteDto;
import ru.mtuci.autonotesbackend.security.SecurityUser;

@Tag(name = "03. Конспекты", description = "API для управления конспектами")
@SecurityRequirement(name = "bearerAuth")
public interface NoteResource {

    @Operation(summary = "Загрузить новый конспект",
            description = "Загружает файл (изображение) и создает новую запись о конспекте со статусом PROCESSING.")
    ResponseEntity<NoteDto> uploadNote(
            @RequestPart("title") String title,
            @RequestPart("file") MultipartFile file,
            SecurityUser securityUser);
}