package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.dto.NoteDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;

//NoteController is very similar to other two
@Controller
public class NoteController {
    private UserService userService;
    private NoteService noteService;

    // constructor:
    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating NoteController bean.");
    }

    @ModelAttribute("noteDTO")
    public NoteDTO getNoteDTO() {
        return new NoteDTO();
    }

    @PostMapping("/home/note/add")
    public String addNewNote(@ModelAttribute("noteDTO") NoteDTO noteDTO, Authentication auth, Model model) {

        String errorMessage = null;

        // use userMapper to get a specific user by username:
        // using Spring Authentication to get username:
        // return current userId:
        int currentUserId = this.userService.getUserID(auth.getName());
        Note note = new Note(noteDTO.getNoteID(),noteDTO.getNoteTitle(),noteDTO.getNoteDescription(),currentUserId);
        // if there are no error, add note based on currentUserId:
        if (errorMessage == null) {
            // set a specific note to current user by userId:
//            note.setUserID(currentUserId);
            // add note to Notes db by noteId:
            int currentNoteId = this.noteService.addNote(note);

            // check if noteId has error, inform error:
            if (currentNoteId < 1) {
                errorMessage = "There was error adding new note!";
            }
        }

        // show result.html page with success/fail message:
        if (errorMessage == null) {
            model.addAttribute("updateSuccess", "Note was successfully added.");
        } else {
            model.addAttribute("updateFail", errorMessage);
        }

        return "result";
    }

    @GetMapping("/home/note/delete/{noteID}")
    public  String deleteNote(@PathVariable("noteID") int noteID, Authentication auth, Model model){
        String errorMessage = null;

        int currentUserId = this.userService.getUserID(auth.getName());


        if (errorMessage == null) {
            int deletedNoteID = this.noteService.deleteNote(noteID);

            if (deletedNoteID < 1) {
                errorMessage = "There was an error deleting this note!";
            }
        }

        if (errorMessage == null) {
            model.addAttribute("updateSuccess", "Note was successfully deleted.");
        } else {
            model.addAttribute("updateFail", errorMessage);
        }

        return "result";

    }
}
