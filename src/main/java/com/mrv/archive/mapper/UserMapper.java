package com.mrv.archive.mapper;


import com.mrv.archive.dto.user.UserDto;
import com.mrv.archive.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto>{
}
