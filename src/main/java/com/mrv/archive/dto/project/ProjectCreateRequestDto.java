package com.mrv.archive.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreateRequestDto {
    private String name;
    private String shortName;
    private String code;
    private Long statusId;
}
