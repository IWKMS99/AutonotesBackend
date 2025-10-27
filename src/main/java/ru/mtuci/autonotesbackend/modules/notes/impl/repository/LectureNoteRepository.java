package ru.mtuci.autonotesbackend.modules.notes.impl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mtuci.autonotesbackend.modules.notes.impl.domain.LectureNote;

import java.util.List;

@Repository
public interface LectureNoteRepository extends JpaRepository<LectureNote, Long> {

    List<LectureNote> findByUserId(Long userId);
}