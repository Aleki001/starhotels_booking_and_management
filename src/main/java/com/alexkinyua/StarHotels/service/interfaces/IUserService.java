package com.alexkinyua.StarHotels.service.interfaces;

import com.alexkinyua.StarHotels.dto.LoginRequest;
import com.alexkinyua.StarHotels.dto.Response;
import com.alexkinyua.StarHotels.entity.User;

public interface IUserService {
    Response register(User loginRequest);
    Response login(LoginRequest loginRequest);
    Response getAllUsers();
    Response getUserBookingHistory(String userId);
    Response deleteUser(String userId);
    Response getUserById(String userId);
    Response getMyInfo(String email);

}
