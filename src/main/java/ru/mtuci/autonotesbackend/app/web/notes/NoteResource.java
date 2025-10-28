package ru.mtuci.autonotesbackend.app.web.notes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.mtuci.autonotesbackend.exception.dto.ErrorResponseDto;
import ru.mtuci.autonotesbackend.modules.notes.api.dto.NoteDto;
import ru.mtuci.autonotesbackend.security.SecurityUser;

@Tag(name = "03. Конспекты", description = "API для управления конспектами")
@SecurityRequirement(name = "bearerAuth")
public interface NoteResource {

    @Operation(
            summary = "Загрузить новый конспект",
            description = "Загружает файл (изображение) и заголовок, создавая новую запись о конспекте"
                    + " со статусом PROCESSING. Запрос должен быть типа `multipart/form-data`.",
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Конспект успешно создан и поставлен в очередь на обработку",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = NoteDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "Некорректный запрос (например, отсутствует файл или заголовок)",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ErrorResponseDto.class))),
                @ApiResponse(
                        responseCode = "401",
                        description = "Пользователь не аутентифицирован",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ErrorResponseDto.class))),
                @ApiResponse(
                        responseCode = "503",
                        description = "Сервис хранения файлов недоступен",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ErrorResponseDto.class)))
            })
    ResponseEntity<NoteDto> uploadNote(
            @Parameter(
                            name = "title",
                            description = "Заголовок конспекта.",
                            required = true,
                            schema = @Schema(type = "string"),
                            example = "Лекция по Дискретной Математике")
                    String title,
            @Parameter(
                            name = "file",
                            description = "Файл изображения конспекта для распознавания.",
                            required = true,
                            content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
                    MultipartFile file,
            @Parameter(hidden = true) SecurityUser securityUser);

    @Operation(
            summary = "Получить все конспекты пользователя",
            description =
                    "Возвращает список всех конспектов, принадлежащих текущему аутентифицированному пользователю.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Список конспектов успешно получен (может быть пустым)."),
                @ApiResponse(
                        responseCode = "401",
                        description = "Пользователь не аутентифицирован",
                        content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
            })
    ResponseEntity<List<NoteDto>> getAllNotes(@Parameter(hidden = true) SecurityUser securityUser);
}
