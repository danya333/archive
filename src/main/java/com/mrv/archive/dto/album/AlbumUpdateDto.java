package com.mrv.archive.dto.album;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumUpdateDto {
    private String name;
    private String shortName;
    private String code;
}
