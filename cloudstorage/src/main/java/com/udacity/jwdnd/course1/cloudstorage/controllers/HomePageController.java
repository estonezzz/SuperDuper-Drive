package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialsDTO;
import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.dto.NoteDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

// different parts of home page are handled by separate controllers
// files, notes, credentials
// Home Page Controller adds everything together
@Controller
@RequestMapping("/home")
public class HomePageController {
    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public HomePageController(UserService userService, FileService fileService, NoteService noteService,
                              CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }
    private List<File> fileList = new ArrayList<>();
    private List<Note> noteList = new ArrayList<>();
    private List<Credentials> credentialsList = new ArrayList<>();

// initialize all the DTOs
    @ModelAttribute("fileDTO")
    public FileDTO getFileDTO() {
        return new FileDTO();
    }

    @ModelAttribute("noteDTO")
    public NoteDTO getNoteDTO() {
        return new NoteDTO();
    }

    @ModelAttribute("credentialsDTO")
    public CredentialsDTO getCredentialsDTO() {
        return new CredentialsDTO();
    }

    @GetMapping
    public String getHomePage(Authentication auth, Model model) {
        int userID = userService.getUserID(auth.getName());

        fileList = this.fileService.getFilesInfo(userID);
        noteList = this.noteService.getAllNotes(userID);
        credentialsList = this.credentialService.getAllCredentials(userID);


        model.addAttribute("files",fileList);
        model.addAttribute("notes",noteList);
        model.addAttribute("credentials", credentialsList);
        model.addAttribute("encryptionService",this.encryptionService);


        return "home";
    }


}
