package com.bookinghotel.controller;

import com.bookinghotel.base.RestApiV1;
import com.bookinghotel.base.VsResponseUtil;
import com.bookinghotel.constant.RoomType;
import com.bookinghotel.constant.UrlConstant;
import com.bookinghotel.dto.RoomCreateDTO;
import com.bookinghotel.dto.RoomUpdateDTO;
import com.bookinghotel.dto.pagination.PaginationSearchSortRequestDTO;
import com.bookinghotel.service.RoomService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class RoomController {

  private final RoomService roomService;

  @ApiOperation("API get room by id")
  @GetMapping(UrlConstant.Room.GET_ROOM)
  public ResponseEntity<?> getRoomById(@PathVariable Long roomId) {
    return VsResponseUtil.ok(roomService.getRoom(roomId));
  }

  @ApiOperation("API get all room")
  @GetMapping(UrlConstant.Room.GET_ROOMS)
  public ResponseEntity<?> getRooms(@Valid PaginationSearchSortRequestDTO requestDTO,
                                    @RequestParam(required = false) RoomType filter) {
    return VsResponseUtil.ok(roomService.getRooms(requestDTO, filter.getValue()));
  }

  @ApiOperation("API create room")
  @PostMapping(UrlConstant.Room.CREATE_ROOM)
  public ResponseEntity<?> updateRoomById(@Valid RoomCreateDTO roomCreateDTO) {
    return VsResponseUtil.ok(roomService.createRoom(roomCreateDTO));
  }

  @ApiOperation("API update room by id")
  @PutMapping(UrlConstant.Room.UPDATE_ROOM)
  public ResponseEntity<?> updateRoomById(@PathVariable Long roomId, @Valid RoomUpdateDTO roomUpdateDTO) {
    return VsResponseUtil.ok(roomService.updateRoom(roomId, roomUpdateDTO));
  }

  @ApiOperation("API delete room by id")
  @DeleteMapping(UrlConstant.Room.DELETE_ROOM)
  public ResponseEntity<?> deleteRoomById(@PathVariable Long roomId) {
    return VsResponseUtil.ok(roomService.deleteRoom(roomId));
  }

}
