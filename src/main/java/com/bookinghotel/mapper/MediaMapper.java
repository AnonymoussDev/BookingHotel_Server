package com.bookinghotel.mapper;

import com.bookinghotel.dto.MediaDTO;
import com.bookinghotel.entity.Media;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MediaMapper {

  Media toMedia(MediaDTO mediaDTO);

  List<Media> toMedias(List<MediaDTO> mediaDTO);

}
