package ru.mtuci.autonotesbackend.modules.notes.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.mtuci.autonotesbackend.modules.notes.impl.domain.NoteStatus;

import java.time.OffsetDateTime;

@Data
@Schema(description = "Информация о конспекте")
public class NoteDto {
    @Schema(description = "ID конспекта", example = "1")
    private Long id;

    @Schema(description = "ID пользователя-владельца", example = "123")
    private Long userId;

    @Schema(description = "Заголовок конспекта", example = "Лекция по высшей математике")
    private String title;

    @Schema(description = "Оригинальное имя загруженного файла", example = "photo_2024-05-20.jpg")
    private String originalFileName;

    @Schema(description = "Статус обработки конспекта")
    private NoteStatus status;

    @Schema(description = "Дата создания")
    private OffsetDateTime createdAt;
}