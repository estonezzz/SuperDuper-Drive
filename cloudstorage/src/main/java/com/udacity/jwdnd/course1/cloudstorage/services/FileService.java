package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

// similar to CredetialService
@Service
public class FileService {
    private FilesMapper filesMapper;

    public FileService(FilesMapper filesMapper) {
        this.filesMapper = filesMapper;
    }

    public List<File> getFilesInfo(int userID){
        return filesMapper.getFilesInfo(userID);
    }

    public int uploadFile(MultipartFile file, int userID) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        File newFile = new File(null,filename, file.getContentType(),file.getSize(),userID,file.getBytes());
        return filesMapper.uploadFile(newFile);
    }
    public File getFile(long fileID){
        return filesMapper.getFile(fileID);
    }

    public File getByFileName(String filename){
        return filesMapper.findByFilename(filename);
    }

    public int deleteFile(long fileID){
         return filesMapper.deleteFile(fileID);
    }
}
