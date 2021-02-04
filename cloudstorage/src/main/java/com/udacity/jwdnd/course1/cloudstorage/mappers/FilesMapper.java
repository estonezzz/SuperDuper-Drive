package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface FilesMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userID}")
    List<File> getFilesInfo(Integer userID);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileID}")
    File getFile(long fileID);

    @Select("SELECT * FROM files WHERE filename = #{filename}")
    public File findByFilename(String filename);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename},#{contentType},#{fileSize},#{userID},#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileID")
    int uploadFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileID}")
    int deleteFile(long fileID);
}
