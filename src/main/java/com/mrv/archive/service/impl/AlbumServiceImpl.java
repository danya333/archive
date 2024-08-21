package com.mrv.archive.service.impl;

import com.mrv.archive.dto.album.AlbumCreateRequestDto;
import com.mrv.archive.model.*;
import com.mrv.archive.repository.AlbumRepository;
import com.mrv.archive.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final SectionService sectionService;
    private final UserService userService;
    private final StatusService statusService;
    private final FileService fileService;

    @Override
    public Album getAlbum(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Album with id " + id + " not found"));
    }

    @Override
    public List<Album> getAllAlbums(Long sectionId) {
        Section section = sectionService.getSection(sectionId);
        return albumRepository.findBySection(section)
                .orElseThrow(() -> new NoSuchElementException("Albums with section id " + sectionId + " not found"));
    }

    @Override
    @Transactional
    public Album create(Long sectionId, AlbumCreateRequestDto albumCreateRequestDto, List<MultipartFile> files) {
        Section section = sectionService.getSection(sectionId);
        User user = userService.getCurrentUser();
        Status status = statusService.getById(albumCreateRequestDto.getStatusId());
        Album album = new Album();
        album.setSection(section);
        album.setCreatedBy(user);
        album.setUpdatedBy(user);
        album.setCreatedAt(LocalDateTime.now());
        album.setUpdatedAt(LocalDateTime.now());
        album.setName(albumCreateRequestDto.getName());
        album.setShortName(albumCreateRequestDto.getShortName());
        album.setCode(albumCreateRequestDto.getCode());
        album.setStatus(status);
        albumRepository.save(album);
        List<File> savedFiles = files.stream()
                .map(file -> fileService.create(file, "Первая версия", album)).toList();
        album.setFiles(savedFiles);
        return albumRepository.save(album);
    }

    @Override
    @Transactional
    public Album addFile(Long albumId, List<MultipartFile> files) {
        Album album = this.getAlbum(albumId);
        album.setUpdatedBy(userService.getCurrentUser());
        files.forEach(file -> album.getFiles().add(fileService.create(file, "Первая версия", album)));
        return albumRepository.save(album);
    }

    @Override
    public Album update(Long sectionId, Album album) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
