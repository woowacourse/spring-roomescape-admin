package roomescape.controller;

import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.storage.ReservationStorage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationStorage reservationStorage;

    public ReservationController(ReservationStorage reservationStorage) {
        this.reservationStorage = reservationStorage;
    }

    @PostMapping
    public ReservationResponse saveReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = fromRequest(reservationRequest);
        Reservation savedReservation = reservationStorage.save(reservation);
        return toResponse(savedReservation);
    }

    private Reservation fromRequest(ReservationRequest reservationRequest) {
        String name = reservationRequest.name();
        LocalDate date = reservationRequest.date();
        LocalTime time = reservationRequest.time();
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        return new Reservation(name, dateTime);
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(reservation.getId(),
                reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @GetMapping
    public List<ReservationResponse> findAllReservations() {
        return reservationStorage.findAllReservations()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @DeleteMapping("/{reservationId}")
    public void delete(@PathVariable long reservationId) {
        reservationStorage.delete(reservationId);
    }
}
