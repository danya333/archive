package com.mrv.archive.dto.stage;

import com.mrv.archive.model.Location;
import com.mrv.archive.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StageCreateDto {
    private String name;
    private Long statusId;
    private List<Long> statusIds;
}