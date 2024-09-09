package com.mrv.archive.dto.section;

import com.mrv.archive.model.Section;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SectionListResponseDto {
    private String city;
    private String stage;
    private String projectName;
    private List<Section> sections;
}
