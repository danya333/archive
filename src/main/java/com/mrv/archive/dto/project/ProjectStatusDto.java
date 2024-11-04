package com.mrv.archive.dto.project;

import com.mrv.archive.dto.status.StatusDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectStatusDto {
    private ProjectCreateRequestDto projectDto;
    private List<StatusDto> statusDtos;
}
