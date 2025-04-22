package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation.Reservations;
import roomescape.domain.ReservationTime.ReservationTime;
import roomescape.domain.ReservationTime.ReservationTimes;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.response.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final Reservations reservations;
    private final ReservationTimes reservationTimes;

    public ReservationController(final Reservations reservations, final ReservationTimes reservationTimes) {
        this.reservations = reservations;
        this.reservationTimes = reservationTimes;
    }

    @GetMapping
    public List<ReservationResponse> findAll() {
        return reservations.findAll().stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime()
                ))
                .toList();
    }

    @PostMapping
    public ResponseEntity<ReservationCreateResponse> create(
            @RequestBody ReservationCreateRequest reservationCreateRequest) {

        ReservationTime time = reservationTimes.findById(reservationCreateRequest.timeId());
        ReservationCreateResponse reservationCreateResponse = new ReservationCreateResponse(
                reservations.create(reservationCreateRequest), reservationCreateRequest.name(),
                reservationCreateRequest.date(), time);

        return ResponseEntity.status(HttpStatus.CREATED).body(reservationCreateResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservations.delete(id);
        return ResponseEntity.noContent().build();
    }
}
