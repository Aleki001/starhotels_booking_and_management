package com.alexkinyua.StarHotels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int statusCode;
    private String message;
    private String token;
    private String role;
    private String bookingConfirmationCode;
    private String expirationTime;
    private UserDto user;
    private RoomDto room;
    private BookingDto booking;
    private List<UserDto> userList;
    private List<RoomDto> roomList;
    private List<BookingDto> bookingList;


}
