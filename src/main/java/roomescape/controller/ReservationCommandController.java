package roomescape.controller;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.dto.ReservationRequestDto;
import roomescape.domain.dto.ReservationTimeRequestDto;
import roomescape.service.ReservationService;

@RestController
public class ReservationCommandController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationCommandController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("reservations")
    public ResponseEntity<Reservation> add(@RequestBody ReservationRequestDto reservationDto) {
        Long id = reservationService.addReservation(reservationDto);
        Reservation reservation = reservationService.readReservationOne(id);
        String location = "/reservations/" + id;
        return ResponseEntity.created(URI.create(location)).body(reservation);
    }

    @DeleteMapping("reservations/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservationId") Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("times")
    public ResponseEntity<ReservationTime> add(@RequestBody ReservationTimeRequestDto reservationTimeDto) {
        Long id = reservationService.addTime(reservationTimeDto);
        ReservationTime reservationTime = reservationService.readTimeOne(id);
        String location = "/times/" + id;
        return ResponseEntity.created(URI.create(location)).body(reservationTime);
    }

    @DeleteMapping("times/{timeId}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable("timeId") Long id) {
        reservationService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
