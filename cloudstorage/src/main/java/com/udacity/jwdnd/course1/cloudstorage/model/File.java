package com.udacity.jwdnd.course1.cloudstorage.model;

import org.springframework.web.multipart.MultipartFile;

public class File {
    private Integer fileID ;
    private String filename;
    private String contentType ;
    private Long fileSize;
    private Integer userID;
    private byte[] fileData;

    public File(Integer fileID, String filename, String contentType, Long fileSize, int userID, byte[] fileData) {
        this.fileID = fileID;
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userID = userID;
        this.fileData = fileData;
    }

    public Integer getFileID() {
        return fileID;
    }

    public void setFileID(Integer fileID) {
        this.fileID = fileID;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
