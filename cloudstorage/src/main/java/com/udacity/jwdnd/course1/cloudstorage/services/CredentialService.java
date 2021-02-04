package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CredentialService {
    private CredentialsMapper credentialsMapper;

    public CredentialService(CredentialsMapper credentialsMapper) {
        this.credentialsMapper = credentialsMapper;
    }

    /** methods: */
    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating Credential Service. ");
    }

// get list of credentials
    public List<Credentials> getAllCredentials(int userID) {
        return this.credentialsMapper.listAllCredentials(userID);
    }

    // add new credentials into DB:
    public int addCredentials(Credentials credentials) {
        // check if a credential is null, then add new credential:
        if (credentials.getCredentialID() == null) {
            return this.credentialsMapper.addCredentials(credentials);
        }
        // else if a credential already exists, update it:
        else {
            return this.credentialsMapper.updateCredentials(credentials);
        }
    }

    // delete a cred by credentialId:
    public int deleteCredential(int credentialID) {
        return this.credentialsMapper.deleteCredentials(credentialID);
    }

    public Credentials getCredentialById(int credentialID) {
        return this.credentialsMapper.getCredentialByID(credentialID);
    }
}

