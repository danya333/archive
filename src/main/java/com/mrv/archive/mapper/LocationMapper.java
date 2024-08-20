package com.mrv.archive.mapper;

import com.mrv.archive.dto.location.LocationDto;
import com.mrv.archive.model.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper extends Mappable<Location, LocationDto>{
}
