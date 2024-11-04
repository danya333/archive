package com.mrv.archive.dto.project;

import com.mrv.archive.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String shortName;
    private String code;
    private LocalDateTime createdAt;
    private List<Status> statuses;
    private Integer completeness;
}
