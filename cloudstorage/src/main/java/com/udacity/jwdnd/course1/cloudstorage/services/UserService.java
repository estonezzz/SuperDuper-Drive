package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    // check if same username exist in DB already
    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    // create new user and hash its password using random salt value
    public int createUser(User user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String EncodedSalt = Base64.getEncoder().encodeToString(salt);
        String HashedPassword = hashService.getHashedValue(user.getPassword(),EncodedSalt);
        return userMapper.insert(new User(null,user.getUsername(),EncodedSalt,HashedPassword,user.getFirstname(),user.getLastname()));
    }
    public int getUserID(String username){
        return userMapper.getUserID(username);
    }


}
