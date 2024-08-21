package com.mrv.archive.service.impl;

import com.mrv.archive.exception.FileDownloadException;
import com.mrv.archive.exception.FileUploadException;
import com.mrv.archive.model.Album;
import com.mrv.archive.model.File;
import com.mrv.archive.repository.FileRepository;
import com.mrv.archive.service.AlbumService;
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
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;
    private final FileRepository fileRepository;
    private final UserService userService;


    @Override
    // Получение файла по id
    public File getFile(Long id) {
        return fileRepository.findById(id).orElseThrow(() -> new NoSuchElementException("File not found"));
    }


    @Override
    public List<File> getFiles(Album album) {
        return fileRepository.findByAlbum(album)
                .orElseThrow(() -> new NoSuchElementException("Files not found for album " + album.getId()));
    }


    @Override
    public byte[] downloadFile(Long fileId) {
        File file = this.getFile(fileId);
        String path = file.getPath();
        byte[] bytes;
        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket("files")
                            .object(path)
                            .build());
            bytes = stream.readAllBytes();
            stream.close();
        } catch (Exception e) {
            throw new FileDownloadException("File download failed: " + e.getMessage());
        }
        return bytes;
    }


    @Override
    // Возвращае byte[] всех файлов принадлежащих конкретному альбому
    public List<byte[]> downloadFiles(Album album) {
        List<String> paths = this.getPaths(album);
        List<byte[]> files = new ArrayList<>();
        for (String path : paths) {
            try {
                InputStream stream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket("files")
                                .object(path)
                                .build());

                byte[] bytes = stream.readAllBytes();
                files.add(bytes);
                stream.close();
            } catch (Exception e) {
                throw new FileDownloadException("File download failed: " + e.getMessage());
            }
        }
        return files;
    }


    @Override
    @Transactional
    // Создание нового файла
    public File create(MultipartFile multipartFile, String description, Album album) {
        File file = newFile(multipartFile, description, 0L);
        file.setVersion(1);
        file.setFileRefId(file.getId());
        file.setAlbum(album);
        fileRepository.save(file);
        return file;
    }


    @Override
    @Transactional
    // Обновление версии файла
    public File updateFileVersion(MultipartFile multipartFile, String description, Long fileRefId) {
        File file = newFile(multipartFile, description, fileRefId);
        file.setVersion(1);
        List<File> files = fileRepository.findByFileRefId(fileRefId)
                .orElseThrow(() -> new NoSuchElementException("Files not found"));
        List<File> sortedFiles = files.stream().sorted(new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.getVersion() > o2.getVersion()) {
                    return 1;
                } else if (o1.getVersion() < o2.getVersion()) {
                    return -1;
                }
                return 0;
            }
        }).toList();
        int lastVersion = sortedFiles.get(sortedFiles.size() - 1).getVersion();
        file.setVersion(lastVersion + 1);
        return file;
    }


    @Override
    @Transactional
    // Загрузка файла в хранилище
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
    public void deleteFile(Long fileId, Album album) {
        if (this.getFile(fileId) != null){
            album.getFiles().remove(this.getFile(fileId));
            fileRepository.deleteById(fileId);
        } else {
            throw new NoSuchElementException("File with id " + fileId + " not found");
        }
    }


    // Возвращает пути к файлам конкретного альбома
    private List<String> getPaths(Album album) {
        List<File> files = fileRepository.findByAlbum(album)
                .orElseThrow(() -> new NoSuchElementException("Files not found"));
        return files.stream().map(File::getPath).toList();
    }


    // Создание экземпляра класса File
    private File newFile(MultipartFile multipartFile, String description, Long fileRefId) {
        File file = new File();
        file.setName(multipartFile.getOriginalFilename());
        file.setUploadedAt(LocalDateTime.now());
        file.setType(multipartFile.getContentType());
        file.setPath(this.upload(multipartFile));
        file.setSize(multipartFile.getSize());
        file.setUser(userService.getCurrentUser());
        file.setDescription(description);
        file.setFileRefId(fileRefId);
        fileRepository.save(file);
        return file;
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
