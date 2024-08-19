package com.mrv.archive.repository;

import com.mrv.archive.model.Stage;
import com.mrv.archive.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface YearRepository extends JpaRepository<Year, Long> {
    Optional<List<Year>> findByStage(Stage stage);
}
