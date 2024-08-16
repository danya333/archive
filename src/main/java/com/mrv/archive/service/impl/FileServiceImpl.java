package com.mrv.archive.service.impl;

import com.mrv.archive.exception.FileDownloadException;
import com.mrv.archive.exception.FileUploadException;
import com.mrv.archive.model.File;
import com.mrv.archive.repository.FileRepository;
import com.mrv.archive.service.FileService;
import com.mrv.archive.service.UserService;
import com.mrv.archive.service.props.MinioProperties;
import io.minio.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;
    private final FileRepository fileRepository;
    private final UserService userService;

    @Override
    @Transactional
    public File create(MultipartFile multipartFile) {
        File file = new File();
        file.setName(multipartFile.getOriginalFilename());
        file.setUploadedAt(LocalDateTime.now());
        file.setType(multipartFile.getContentType());
        file.setPath(this.upload(multipartFile));
        file.setSize(multipartFile.getSize());
        file.setUser(userService.getCurrentUser());
        file.setDescription("First version");
        fileRepository.save(file);
        return file;
    }

    @Override
    @Transactional
    public String upload(MultipartFile file) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new FileUploadException("File upload failed: "
                    + e.getMessage());
        }
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new FileUploadException("File must have name.");
        }
        String filePath = generateFilePath(file);
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new FileUploadException("File upload failed: "
                    + e.getMessage());
        }
        saveFile(inputStream, filePath);
        return filePath;
    }

    @Override
    public List<byte[]> download(Long tagId){
        List<String> files = this.getFiles(tagId);
        List<byte[]> fileContents = new ArrayList<>();
        for (String filePath : files) {
            try {
                InputStream stream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket("files")
                                .object(filePath)
                                .build());

                byte[] bytes = stream.readAllBytes();
                fileContents.add(bytes);
                stream.close();
            } catch (Exception e) {
                throw new FileDownloadException("File download failed: " + e.getMessage());
            }
        }
        return fileContents;
    }

    @Override
    public List<String> getFiles(Long tagId) {

        return null;
    }

    @Override
    public File getFile(Long id) {
        return fileRepository.findById(id).orElseThrow(() -> new NoSuchElementException("File not found"));
    }


    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    private String generateFilePath(MultipartFile file) {
        String fileName = file.getOriginalFilename().replace(' ', '_');
        return UUID.randomUUID() + "_" + fileName;
    }


    @SneakyThrows
    private void saveFile(InputStream inputStream, String filePath) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(filePath)
                .build());
    }
}
