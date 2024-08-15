package com.mrv.archive.repository;

import com.mrv.archive.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface FileRepository extends JpaRepository<File, Long> {

}
