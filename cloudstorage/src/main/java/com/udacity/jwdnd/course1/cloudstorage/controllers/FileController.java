package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileController {
    private FileService fileService;
    private UserService userService;
//    private List<File> fileList = new ArrayList<>();
    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }
    // Data Transfer Object with file related info
    @ModelAttribute("fileDTO")
    public FileDTO getFileDTO(){
        return new FileDTO();
    }

    @PostMapping("home/file/upload")
    public String uploadNewFile(Authentication auth, Model model, @ModelAttribute("fileDTO") MultipartFile file) throws IOException {
        String errorMessage = null;
        int currentUserID = this.userService.getUserID(auth.getName());

        // handle edge cases:
        // when empty file is uploaded:
        if (file.isEmpty()) {
            errorMessage = "File is empty!";
        }
        if (errorMessage == null){
            String filename = file.getOriginalFilename();
            if (fileService.getByFileName(filename) != null){
                errorMessage = "Can't upload this file. FIle with this name already exists.";
            }
        }
        if (errorMessage == null) {
            // upload file to  DB by fileId:
            // return number ow rows added if success:
            int fileRowsAdded = this.fileService.uploadFile(file, currentUserID);
            if (fileRowsAdded < 0) {
                errorMessage = "There was an error uploading this file!";
            }
        }
        // show result.html page with success/fail message:
        if (errorMessage == null) {
            model.addAttribute("updateSuccess","File was successfully uploaded.");
        } else {
            model.addAttribute("updateFail", errorMessage);
        }
        return "result";

    }


    @GetMapping("/home/file/delete/{fileID}")
    public String deleteFile(@PathVariable long fileID, Authentication auth, Model model) {
        String errorMessage = null;
        int currentUserID = this.userService.getUserID(auth.getName());

        if (errorMessage == null) {
            int deletedFileID = this.fileService.deleteFile(fileID);

            if (deletedFileID < 0) {
                errorMessage = "An error occurred when  deleting this file!";
            }
        }

        if (errorMessage == null) {
            model.addAttribute("updateSuccess", "File was successfully deleted.");
        } else {
            model.addAttribute("updateFail", errorMessage);
        }

        return "result";
    }
    // delete files with @GetMapping and @PathVariable received
    @GetMapping("home/file/view/{fileID}")
    public ResponseEntity<Resource> viewFile(@PathVariable Long fileID, Authentication auth)  {
        File file = fileService.getFile(fileID);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

}
