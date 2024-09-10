package com.mrv.archive.dto.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {
    private Long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private Boolean isActive;
}
