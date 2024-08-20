package com.mrv.archive.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stages")
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @JsonBackReference
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @JsonBackReference
    private Status status;

    @OneToMany(mappedBy = "stage", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<StageStatus> stageStatuses = new ArrayList<>();

    @OneToMany(mappedBy = "stage", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Project> projects = new ArrayList<>();



}
