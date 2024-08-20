package com.mrv.archive.controller;

import com.mrv.archive.model.File;
import com.mrv.archive.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locations/{locationId}/stages/{stageId}/projects/{projectId}/sections/{sectionId}/albums/" +
        "{albumId}/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/{id}")
    public ResponseEntity<File> getFile(@PathVariable Long id){
        File file = fileService.getFile(id);
        return ResponseEntity.ok(file);
    }






}
