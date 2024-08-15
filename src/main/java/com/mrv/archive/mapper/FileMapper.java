package com.mrv.archive.mapper;

import com.mrv.archive.dto.file.FileDto;
import com.mrv.archive.model.File;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper extends Mappable<File, FileDto> {
}
