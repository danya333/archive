package com.mrv.archive.controller;

import com.mrv.archive.model.Album;
import com.mrv.archive.model.File;
import com.mrv.archive.service.AlbumService;
import com.mrv.archive.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/locations/{locationId}/stages/{stageId}/projects/{projectId}/sections/{sectionId}/albums/" +
        "{albumId}/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final AlbumService albumService;

    @GetMapping("/{id}")
    public ResponseEntity<File> getFile(@PathVariable Long id) {
        File file = fileService.getFile(id);
        return ResponseEntity.ok(file);
    }


    @GetMapping("/{id}/download")
    public ResponseEntity<List<byte[]>> downloadFile(@PathVariable Long id) {
        List<byte[]> bytes = fileService.downloadFile(id);
        return new ResponseEntity<>(bytes, HttpStatus.OK);
    }


    @GetMapping("/list")
    public ResponseEntity<List<File>> listFiles(@PathVariable Long albumId) {
        Album album = albumService.getAlbum(albumId);
        List<File> files = fileService.getFiles(album);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }


    @PostMapping("/{fileRefId}/update")
    public ResponseEntity<File> updateFileVersion(@PathVariable Long fileRefId,
                                                  @RequestParam String description,
                                                  @RequestParam MultipartFile file) {
        File parentFile = fileService.getFile(fileRefId);
        log.info("Updating file {} with description {}", fileRefId, description);
        File response = fileService.updateFileVersion(file, description, fileRefId, parentFile.getAlbum());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id,
                                           @PathVariable Long albumId) {
        Album album = albumService.getAlbum(albumId);
        fileService.deleteFile(id, album);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
