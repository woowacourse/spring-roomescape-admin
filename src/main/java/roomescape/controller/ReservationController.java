package roomescape.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    private ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> findAllReservations() {
        List<ReservationResponse> reservationResponses = reservationDao.findAll()
                .stream()
                .map(ReservationResponse::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationCreateRequest reservationCreateRequest) {
        Reservation reservation = new Reservation(
                index.getAndIncrement(),
                reservationCreateRequest.name(),
                reservationCreateRequest.date(),
                reservationCreateRequest.time());

        reservations.add(reservation);
        return ResponseEntity.ok()
                .body(ReservationResponse.toResponse(reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        Optional<Reservation> foundReservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst();

        if (foundReservation.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("삭제할 예약이 존재하지 않습니다.");
        }

        Reservation reservation = foundReservation.get();
        reservations.remove(reservation);
        return ResponseEntity.ok()
                .build();
    }
}
