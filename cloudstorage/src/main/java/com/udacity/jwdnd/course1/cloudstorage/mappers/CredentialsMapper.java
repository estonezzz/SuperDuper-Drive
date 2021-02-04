package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    // get all credentials from Credential DB by userID:
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userID}")
    List<Credentials> listAllCredentials(int userID);

    // add new url, username, password into s DB, primary key autoincremented
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES (#{url}, #{username}, #{key}, #{password}, #{userID});")
    @Options(useGeneratedKeys = true, keyProperty = "credentialID")
    int addCredentials(Credentials credentials);

    // edit  new credentials by credentialId:
    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key}, password=#{password} " +
            "WHERE (credentialid=#{credentialID})")
    int updateCredentials(Credentials credentials);

    // delete a credential by credentialID:
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialID}")
    int deleteCredentials(int credentialID);

    // get a credential by its credentialID
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialID}")
    Credentials getCredentialByID(int credentialID);
}
