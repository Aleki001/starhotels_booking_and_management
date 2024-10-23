package com.alexkinyua.StarHotels.service.interfaces;

import com.alexkinyua.StarHotels.dto.Response;
import com.alexkinyua.StarHotels.entity.Booking;

public interface IBookingService {
    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);
}
