package com.mrv.archive.service;

import com.mrv.archive.model.Album;
import com.mrv.archive.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    File getFile(Long id);
    List<File> getFiles(Album album);
    byte[] downloadFile(Long fileId);
    List<byte[]> downloadFiles(Album album);
    File create(MultipartFile multipartFile, String description, Album album);
    File updateFileVersion(MultipartFile multipartFile, String description, Long fileRefId);
    String upload(MultipartFile file);
    void deleteFile(Long fileId, Album album);

}
