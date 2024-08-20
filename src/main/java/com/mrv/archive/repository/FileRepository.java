package com.mrv.archive.repository;

import com.mrv.archive.model.Album;
import com.mrv.archive.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;


public interface FileRepository extends JpaRepository<File, Long> {
    Optional<List<File>> findByAlbum(Album album);
    Optional<List<File>> findByFileRefId(Long fileRefId);
}
