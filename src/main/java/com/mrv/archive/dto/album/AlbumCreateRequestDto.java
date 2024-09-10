package com.mrv.archive.dto.album;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumCreateRequestDto {
    private String name;
    private String shortName;
    private String code;
    private Long statusId;
    private String description;
    private Integer completeness;
}
