package com.mycompany.server.model;

import com.google.gson.Gson;

public class FileMetadata {
    private String fileName;
    private String fileId;
    private long size;
    private String extension;

    public FileMetadata(String fileName, String fileId, long size, String extension) {
        this.fileName = fileName;
        this.fileId = fileId;
        this.size = size;
        this.extension = extension;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    // Getters y Setters
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
