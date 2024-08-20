package com.mrv.archive.mapper;

import com.mrv.archive.dto.section.SectionCreateDto;
import com.mrv.archive.model.Section;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SectionCreateDtoMapper extends Mappable<Section, SectionCreateDto>{
}
