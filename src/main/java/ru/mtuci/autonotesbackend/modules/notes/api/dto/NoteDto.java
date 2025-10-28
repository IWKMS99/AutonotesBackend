package ru.mtuci.autonotesbackend.modules.notes.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.mtuci.autonotesbackend.modules.notes.impl.domain.NoteStatus;

import java.time.OffsetDateTime;

@Data
@Schema(description = "Краткая информация о конспекте")
public class NoteDto {
    @Schema(description = "ID конспекта", example = "42")
    private Long id;

    @Schema(description = "ID пользователя-владельца", example = "1")
    private Long userId;

    @Schema(description = "Заголовок конспекта", example = "Лекция по теории вероятностей")
    private String title;

    @Schema(description = "Оригинальное имя загруженного файла", example = "IMG_20240521_1030.jpg")
    private String originalFileName;

    @Schema(description = "Статус обработки конспекта", example = "PROCESSING")
    private NoteStatus status;

    @Schema(description = "Дата создания")
    private OffsetDateTime createdAt;
}