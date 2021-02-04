package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialsDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class CredentialsController {
    private UserService userService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public CredentialsController(UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @ModelAttribute("credentialsDTO")
    public CredentialsDTO getCredentialsDTO() {
        return new CredentialsDTO();
    }

    @PostConstruct()
    public void postConstruct() {
        System.out.println("Creating Credential Controller bean");
    }

    // ADD new credential to CredentialDB:
    @PostMapping("/home/credentials/add")
    public String postNewCredential(@ModelAttribute("credentialsDTO") CredentialsDTO credentialsDTO, Model model, Authentication auth) {

        String errorMessage = null;

        int currentUserID = this.userService.getUserID(auth.getName());

        // create the credential variable that will be
        // responsible for receiving the credentialDTO variable data:
        Credentials credentials = new Credentials(credentialsDTO.getCredentialID(),credentialsDTO.getUrl(),
                credentialsDTO.getUsername(),null,credentialsDTO.getPassword(),null);

        // if there is no error, add new credential:
        if (errorMessage == null) {
            credentials.setUserID(currentUserID);

            // encrypt password
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);

            // set new encoded key
            credentials.setKey(encodedKey);

            // set encrypted password
            String encryptedPassword = this.encryptionService.encryptValue(credentials.getPassword(), credentials.getKey());
            credentials.setPassword(encryptedPassword);

            // add new credential to DB
            int currentCredentialID = this.credentialService.addCredentials(credentials);
            System.out.println(currentCredentialID);

            // check if there are errors adding new credential, display error:
            if (currentCredentialID < 0) {
                errorMessage = "Error occurred when adding new credentials.";
            }
        }

        // show result.html page with success/fail message:
        if (errorMessage == null) {
            model.addAttribute("updateSuccess","Your credentials were successfully added.");
        } else {
            model.addAttribute("updateFail", errorMessage);
        }

        return "result";
    }

    // DELETE a credential by its id:
    @GetMapping("/home/credentials/delete/{credentialID}")
    public String deleteCredentials(@PathVariable("credentialID") int credentialID, Model model) {

        String errorMessage = null;

        if (errorMessage == null) {
            // delete a credential by its credentialId:
            // return a successfully deleted credentialId:
            int deletedCredentialID = this.credentialService.deleteCredential(credentialID);

            // check if there is error deleting a credential, display error msg:
            if (deletedCredentialID < 1) {
                errorMessage = "There was error deleting this credential.";
            }
        }

        if (errorMessage == null) {
            model.addAttribute("updateSuccess", "Your credentials were successfully deleted. ");
        } else {
            model.addAttribute("updateFail", errorMessage);
        }

        return "result";
    }

    // decrypt password by to show decrypted password when open up modal on frontend:
    // returns decrypted password
    @GetMapping("/home/credentials/pass/{credentialID}")
    public ResponseEntity<String> decryptPassword(@PathVariable("credentialID") int credentialID) throws IOException {
        Credentials credentials = this.credentialService.getCredentialById(credentialID);
        // use .decryptValue in EncryptionService to decrypt password:
        String decryptedPassword = this.encryptionService.decryptValue(credentials.getPassword(), credentials.getKey());
//        System.out.println("Decrypted password " + decryptedPassword);


        // return the decrypted password back as HTTP response:
        return ResponseEntity.ok(decryptedPassword);
    }
}
