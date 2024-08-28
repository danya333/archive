package com.mrv.archive.dto.stage;

import com.mrv.archive.model.Location;
import com.mrv.archive.model.Project;
import com.mrv.archive.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StageResponseDto {
    private Long id;
    private String name;
    private Location location;
    private Status status;
    private List<Status> availableStatuses;
    private List<Project> projects;
    private List<Integer> projectYears;
}
