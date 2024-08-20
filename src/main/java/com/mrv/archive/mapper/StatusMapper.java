package com.mrv.archive.mapper;

import com.mrv.archive.dto.status.StatusDto;
import com.mrv.archive.model.Status;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper extends Mappable<Status, StatusDto> {
}
