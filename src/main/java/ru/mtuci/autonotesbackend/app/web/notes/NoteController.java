package ru.mtuci.autonotesbackend.app.web.notes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.mtuci.autonotesbackend.modules.notes.api.NoteFacade;
import ru.mtuci.autonotesbackend.modules.notes.api.dto.NoteDto;
import ru.mtuci.autonotesbackend.security.SecurityUser;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController implements NoteResource {

    private final NoteFacade noteFacade;

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NoteDto> uploadNote(
            @RequestPart("title") String title,
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal SecurityUser securityUser) {

        NoteDto createdNote = noteFacade.createNote(title, file, securityUser.user().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }
}