package com.mrv.archive.model;

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
@Table(name = "statuses")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "status", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<ProjectStatus> projectStatuses = new ArrayList<>();

    @OneToMany(mappedBy = "status", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Project> projects = new ArrayList<>();
}
