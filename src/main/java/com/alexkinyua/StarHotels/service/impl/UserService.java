package com.alexkinyua.StarHotels.service.impl;

import com.alexkinyua.StarHotels.dto.LoginRequest;
import com.alexkinyua.StarHotels.dto.Response;
import com.alexkinyua.StarHotels.dto.UserDto;
import com.alexkinyua.StarHotels.entity.User;
import com.alexkinyua.StarHotels.exception.OurException;
import com.alexkinyua.StarHotels.repository.UserRepository;
import com.alexkinyua.StarHotels.security.jwt.JwtTokenProvider;
import com.alexkinyua.StarHotels.service.interfaces.IUserService;
import com.alexkinyua.StarHotels.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public Response register(User user) {
        Response response = new Response();
        try {
            if (user.getRole() == null || user.getRole().isBlank()){
                user.setRole("USER");
            }
            if (userRepository.existsByEmail(user.getEmail())){
                throw new OurException(user.getEmail() + " Already exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User saveUser = userRepository.save(user);
            UserDto userDto = Utils.mapUserEntityToUserDto(saveUser);
            response.setStatusCode(200);
            response.setUser(userDto);
        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred during user registration "+ e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurException("User not found"));
            var token = jwtTokenProvider.generateToken(authentication);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 Days");
            response.setMessage("Successful");
        }catch (OurException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred during user login "+ e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try {
            List<User> userList = userRepository.findAll();
            List<UserDto> userDtoList = Utils.mapUserListEntityToUserListDto(userList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUserList(userDtoList);
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting all users "+ e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        Response response = new Response();
        try{
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User not found"));
            UserDto userDto = Utils.mapUserEntityToUserDtoPlusUserBookingsAndRooms(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDto);

        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting all users "+ e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();
        try{
            userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User not found"));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("successful");

        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error deleting a user "+ e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response = new Response();
        try{
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User not found"));
            UserDto userDto = Utils.mapUserEntityToUserDto(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDto);

        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error finding a user "+ e.getMessage());
        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();
        try{
            User user = userRepository.findByEmail(email).orElseThrow(() -> new OurException("User not found"));
            UserDto userDto = Utils.mapUserEntityToUserDto(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUser(userDto);

        }catch (OurException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error finding a user "+ e.getMessage());
        }
        return response;
    }
}
