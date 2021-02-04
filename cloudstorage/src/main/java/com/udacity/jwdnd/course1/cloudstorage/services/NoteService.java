package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {
    // use MyBatis mapper to access to database:
    private NotesMapper notesMapper;


    public NoteService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }


    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating NotesService bean");
    }

    // get all notes from DB:
    // use in HomePageController:
    public List<Note> getAllNotes(int userID) {
        return notesMapper.showAllNotes(userID);
    }

    // add/update a note from DB:
    public int addNote(Note note) {
        // check if a noteId is null, then add new note by noteId:
        if (note.getNoteID() == null) {
            return this.notesMapper.addNote(note);
        }
        // if noteId is NOT null, meaning it exists in Notes db
        // then update note with new title and description:
        else {
            return this.notesMapper.updateNote(note);
        }
    }

    // delete note:
    public int deleteNote(int noteID) {
        return notesMapper.deleteNote(noteID);
    }
}
