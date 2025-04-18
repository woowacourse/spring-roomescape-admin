package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import roomescape.dto.CreateReservationDto;
import roomescape.model.Reservation;
import roomescape.model.ReservationGroup;

import java.util.List;

@RestController
public class ReservationApiController {

    ReservationGroup reservations = new ReservationGroup();

    @GetMapping("/reservations")
    public List<Reservation> getAllReservations() {
        return reservations.getReservations();
    }

    @PostMapping("/reservations")
    public Reservation addReservation(@RequestBody CreateReservationDto reservationDto) {
        try {
            Reservation newReservation = reservationDto.convertToEntity(reservations.getIndexAndIncrement());
            reservations.addReservation(newReservation);
            return newReservation;
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable("id") Long id) {
        reservations.deleteReservationById(id);
    }
}
