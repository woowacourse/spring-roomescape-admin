package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import roomescape.dto.CreateReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationGroup;

import java.util.List;

@RestController
public class ReservationApiController {

    ReservationGroup reservations = new ReservationGroup();

    @GetMapping("/reservations")
    public List<ReservationResponseDto> getAllReservations() {
        return reservations.getReservations().stream()
                .map(reservation -> new ReservationResponseDto(
                        reservation.id(),
                        reservation.name(),
                        reservation.date(),
                        reservation.time()
                ))
                .toList();
    }

    @PostMapping("/reservations")
    public ReservationResponseDto addReservation(@RequestBody CreateReservationRequestDto reservationDto) {
        try {
            Reservation newReservation = reservationDto.toEntity(reservations.getIndexAndIncrement());
            reservations.addReservation(newReservation);

            ReservationResponseDto responseDto = new ReservationResponseDto(
                    newReservation.id(),
                    newReservation.name(),
                    newReservation.date(),
                    newReservation.time());
            return responseDto;
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable("id") Long id) {
        reservations.deleteReservationById(id);
    }
}
