package com.mrv.archive.dto.stage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StageCreateDto {
    private String name;
    private String shortName;
}
