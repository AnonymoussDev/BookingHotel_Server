package com.bookinghotel.mapper;

import com.bookinghotel.dto.MediaDTO;
import com.bookinghotel.dto.RoomCreateDTO;
import com.bookinghotel.dto.RoomDTO;
import com.bookinghotel.dto.RoomUpdateDTO;
import com.bookinghotel.entity.Media;
import com.bookinghotel.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

  RoomDTO toRoomDTO(Room room);

  List<RoomDTO> toRoomDTOs(List<Room> rooms);

  MediaDTO toMediaDTO(Media media);

  Room createDtoToRoom(RoomCreateDTO createDTO);

  @Mapping(target = "medias", ignore = true)
  void updateRoomFromDTO(RoomUpdateDTO updateDTO, @MappingTarget Room room);

}
