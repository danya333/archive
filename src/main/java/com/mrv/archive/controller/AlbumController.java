package com.mrv.archive.controller;

import com.mrv.archive.dto.album.AlbumCompletenessDto;
import com.mrv.archive.dto.album.AlbumCreateRequestDto;
import com.mrv.archive.dto.album.AlbumUpdateDto;
import com.mrv.archive.model.Album;
import com.mrv.archive.service.AlbumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/sections/{sectionId}/albums")
@RequiredArgsConstructor
@Slf4j
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

    @PutMapping("/{albumId}")
    public ResponseEntity<Void> updateAlbum(@PathVariable Long sectionId,
                                            @PathVariable Long albumId,
                                            @RequestBody AlbumUpdateDto albumUpdateDto){
        try{
            albumService.update(albumId, albumUpdateDto);
        } catch (Exception e){
            log.error("Error accured updating album with id: {}", albumId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{albumId}/add")
    public ResponseEntity<Album> addFile(@PathVariable Long albumId,
                                         @RequestPart List<MultipartFile> files){
        Album album = albumService.addFile(albumId, files);
        return new ResponseEntity<>(album, HttpStatus.OK);
    }

    @PutMapping("/{albumId}/completeness")
    public ResponseEntity<Void> updateCompleteness(@PathVariable Long albumId, @RequestBody AlbumCompletenessDto completeness){
        albumService.updateCompleteness(albumId, completeness.getCompleteness());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id){
        albumService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
