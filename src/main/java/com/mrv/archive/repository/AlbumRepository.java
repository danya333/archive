package com.mrv.archive.repository;

import com.mrv.archive.model.Album;
import com.mrv.archive.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<List<Album>> findBySection(Section section);
}
