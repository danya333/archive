package com.mrv.archive.service;

import com.mrv.archive.dto.album.AlbumCreateRequestDto;
import com.mrv.archive.dto.album.AlbumUpdateDto;
import com.mrv.archive.model.Album;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AlbumService {
    Album getAlbum(Long id);
    List<Album> getAllAlbums(Long sectionId);
    Album create(Long sectionId, AlbumCreateRequestDto albumCreateRequestDto, List<MultipartFile> files);
    Album addFile(Long albumId, List<MultipartFile> files);
    Album update(Long albumId, AlbumUpdateDto albumUpdateDto);
    void updateCompleteness(Long albumId, Integer completeness);
    void delete(Long id);
}
