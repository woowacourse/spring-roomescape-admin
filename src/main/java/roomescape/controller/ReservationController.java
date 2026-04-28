package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.util.DateAndTimeConverter;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;

import java.util.List;

@RequestMapping("/reservations")
@RestController
public class ReservationController {
    private final Reservations reservations = new Reservations();

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody CreateReservationRequest createReservationRequest) {
        Reservation reservation = new Reservation(
                createReservationRequest.name(),
                DateAndTimeConverter.parseToDate(createReservationRequest.date()),
                DateAndTimeConverter.parseToTime(createReservationRequest.time())
        );

        ReservationResponse reservationResponse = new ReservationResponse(
                reservations.add(reservation),
                reservation.getName(),
                DateAndTimeConverter.formatDate(reservation.getDate()),
                DateAndTimeConverter.formatTime(reservation.getTime())
        );

        return ResponseEntity.ok(reservationResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations(){
       List<ReservationResponse> reservationResponses = reservations.getAllReservations()
                .stream()
                .map(reservation -> new ReservationResponse(
                       reservations.getReservationId(reservation),
                        reservation.getName(),
                        DateAndTimeConverter.formatDate(reservation.getDate()),
                        DateAndTimeConverter.formatTime(reservation.getTime())
                ))
                .toList();

       return ResponseEntity.ok(reservationResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservations.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
