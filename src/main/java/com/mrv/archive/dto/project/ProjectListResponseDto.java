package com.mrv.archive.dto.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectListResponseDto {
    private Long id;
    private String name;
    private String shortName;
    private String code;
    private LocalDateTime createdAt;
    private String status;
}
