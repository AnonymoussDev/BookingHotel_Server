package com.bookinghotel.mapper;

import com.bookinghotel.dto.UserCreateDTO;
import com.bookinghotel.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User toUser(UserCreateDTO userCreateDTO);

}
