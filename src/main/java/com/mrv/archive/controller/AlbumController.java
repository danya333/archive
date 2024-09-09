package com.mrv.archive.controller;

import com.mrv.archive.dto.album.AlbumCreateRequestDto;
import com.mrv.archive.model.Album;
import com.mrv.archive.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/sections/{sectionId}/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbum(@PathVariable long id) {
        Album album = albumService.getAlbum(id);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Album>> getAllAlbums(@PathVariable Long sectionId){
        List<Album> albums = albumService.getAllAlbums(sectionId);
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Album> createAlbum(@PathVariable Long sectionId,
                                             @RequestPart AlbumCreateRequestDto albumDto,
                                             @RequestPart("files") List<MultipartFile> files){
        Album album = albumService.create(sectionId, albumDto, files);
        return new ResponseEntity<>(album, HttpStatus.CREATED);
    }

    @PostMapping("/{albumId}/add")
    public ResponseEntity<Album> addFile(@PathVariable Long albumId,
                                         @RequestPart List<MultipartFile> files){
        Album album = albumService.addFile(albumId, files);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id){
        albumService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
