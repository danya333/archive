package com.mrv.archive.dto.section;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionCreateDto {
    private String name;
    private String shortName;
    private String code;
}
