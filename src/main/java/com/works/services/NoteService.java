package com.works.services;

import com.works.entities.Note;
import com.works.entities.dtos.NoteDto;
import com.works.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final ModelMapper modelMapperDefault;

    public Note save(NoteDto noteDto) {
        Note note = modelMapperDefault.map(noteDto, Note.class);
        return noteRepository.save(note);
    }

    public List<Note> list() {
        return noteRepository.findAll();
    }

    public boolean delete(Long id) {
        try {
            noteRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
