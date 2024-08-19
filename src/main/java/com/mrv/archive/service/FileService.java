package com.mrv.archive.service;

import com.mrv.archive.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    File create(MultipartFile multipartFile, String description);
    File updateFileVersion(MultipartFile multipartFile, String description, Long fileRefId);
    String upload(MultipartFile file);

    List<byte[]> download(Long id);

    List<String> getFiles(Long tagId);

    File getFile(Long id);

}
