package com.works.restcontrollers;

import com.works.entities.Note;
import com.works.entities.dtos.NoteDto;
import com.works.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("note")
public class NoteRestController {

    private final NoteService noteService;

    @PostMapping("save")
    public Note save(@RequestBody NoteDto noteDto) {
        return noteService.save(noteDto);
    }

    @GetMapping("list")
    public List<Note> list() {
        return noteService.list();
    }

    @DeleteMapping("delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return noteService.delete(id);
    }
}
