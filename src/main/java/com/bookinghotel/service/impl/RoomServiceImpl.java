package com.bookinghotel.service.impl;

import com.bookinghotel.constant.CommonConstant;
import com.bookinghotel.constant.CommonMessage;
import com.bookinghotel.constant.ErrorMessage;
import com.bookinghotel.constant.SortByDataConstant;
import com.bookinghotel.dto.MediaDTO;
import com.bookinghotel.dto.RoomCreateDTO;
import com.bookinghotel.dto.RoomDTO;
import com.bookinghotel.dto.RoomUpdateDTO;
import com.bookinghotel.dto.common.CommonResponseDTO;
import com.bookinghotel.dto.pagination.PaginationResponseDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.dto.pagination.PagingMeta;
import com.bookinghotel.entity.Media;
import com.bookinghotel.entity.Room;
import com.bookinghotel.exception.InternalServerException;
import com.bookinghotel.exception.InvalidException;
import com.bookinghotel.exception.NotFoundException;
import com.bookinghotel.mapper.MediaMapper;
import com.bookinghotel.mapper.RoomMapper;
import com.bookinghotel.repository.MediaRepository;
import com.bookinghotel.repository.RoomRepository;
import com.bookinghotel.service.RoomService;
import com.bookinghotel.util.PaginationUtil;
import com.bookinghotel.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;

  private final MediaRepository mediaRepository;

  private final RoomMapper roomMapper;

  private final MediaMapper mediaMapper;

  private final UploadFileUtil uploadFile;

  @Override
  public RoomDTO getRoom(Long roomId) {
    Optional<Room> room = roomRepository.findById(roomId);
    checkNotFoundRoomById(room, roomId);
    room.get().setMedias(mediaRepository.findByRoomToSet(roomId));
    return roomMapper.toRoomDTO(room.get());
  }

  @Override
  public PaginationResponseDTO<RoomDTO> getRooms(PaginationSearchSortRequestDTO requestDTO, String filter) {
    //Pagination
    Pageable pageable = PaginationUtil.buildPageable(requestDTO, SortByDataConstant.ROOM);
    Page<Room> rooms = roomRepository.findAllByKey(requestDTO.getKeyword(), filter, pageable);

    //Create Output
    PagingMeta meta = PaginationUtil.buildPagingMeta(requestDTO, SortByDataConstant.ROOM, rooms);
    List<RoomDTO> roomDTOs = roomMapper.toRoomDTOs(rooms.getContent());
    return new PaginationResponseDTO<RoomDTO>(meta, roomDTOs);
  }

  @Override
  public RoomDTO createRoom(RoomCreateDTO roomCreateDTO) {
    Room room = roomMapper.createDtoToRoom(roomCreateDTO);
    roomRepository.save(room);
    room.setMedias(toMedias(room, roomCreateDTO.getFiles()));
    return roomMapper.toRoomDTO(roomRepository.save(room));
  }

  @Override
  public RoomDTO updateRoom(Long roomId, RoomUpdateDTO roomUpdateDTO) {
    Optional<Room> currentRoom = roomRepository.findById(roomId);
    checkNotFoundRoomById(currentRoom, roomId);

    //Delete media if not found in mediaDTO
    if(CollectionUtils.isNotEmpty(roomUpdateDTO.getMedias())) {
      List<Media> medias = mediaMapper.toMedias(roomUpdateDTO.getMedias());
      List<Media> mediaDeleteFlag = mediaRepository.findByRoomIdAndNotInMedia(roomId, medias);
      if (CollectionUtils.isNotEmpty(mediaDeleteFlag)) {
        mediaDeleteFlag.forEach(item -> {
          item.setDeleteFlag(CommonConstant.TRUE);
          mediaRepository.save(item);
        });
      }
    } else {
      throw new InvalidException(ErrorMessage.Room.ERR_NO_PHOTO);
    }
    //add file if exist
    if(roomUpdateDTO.getFiles() != null) {
      currentRoom.get().getMedias().addAll(toMedias(currentRoom.get(), roomUpdateDTO.getFiles()));
    }

    roomMapper.updateRoomFromDTO(roomUpdateDTO, currentRoom.get());
    currentRoom.get().setMedias(mediaRepository.findByRoomToSet(roomId));
    return roomMapper.toRoomDTO(roomRepository.save(currentRoom.get()));
  }

  @Override
  public CommonResponseDTO deleteRoom(Long roomId) {
    Optional<Room> currentRoom = roomRepository.findById(roomId);
    checkNotFoundRoomById(currentRoom, roomId);
    try {
      currentRoom.get().setDeleteFlag(CommonConstant.TRUE);
      //set deleteFlag Media
      List<Media> mediaDeleteFlag = mediaRepository.findByRoomId(roomId);
      if (CollectionUtils.isNotEmpty(mediaDeleteFlag)) {
        mediaDeleteFlag.forEach(item -> {
          item.setDeleteFlag(CommonConstant.TRUE);
          mediaRepository.save(item);
        });
      }
      roomRepository.save(currentRoom.get());
      return new CommonResponseDTO(CommonConstant.TRUE, CommonMessage.DELETE_SUCCESS);
    } catch (Exception e) {
      throw new InternalServerException(ErrorMessage.ERR_EXCEPTION_GENERAL);
    }
  }

  private Set<Media> toMedias(Room room, List<MultipartFile> files) {
    Set<Media> medias = new HashSet<>();
    for(MultipartFile file : files) {
      Media media = new Media();
      media.setUrl(uploadFile.getUrlFromFile(file));
      media.setRoom(room);
      mediaRepository.save(media);
      medias.add(media);
    }
    return medias;
  }

  private void checkNotFoundRoomById(Optional<Room> room, Long roomId) {
    if (room.isEmpty()) {
      throw new NotFoundException(String.format(ErrorMessage.Room.ERR_NOT_FOUND_ID, roomId));
    }
  }

}
