package ru.mtuci.autonotesbackend.modules.notes.api;

import org.springframework.web.multipart.MultipartFile;
import ru.mtuci.autonotesbackend.modules.notes.api.dto.NoteDto;

public interface NoteFacade {
    NoteDto createNote(String title, MultipartFile file, Long userId);
}
