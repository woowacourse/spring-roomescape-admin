package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;

    public ReservationController(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> readReservations() {
        List<Reservation> reservations = reservationDao.findAllReservations();
        List<ReservationResponse> dtos = ReservationResponse.from(reservations);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation();
        long id = reservationDao.insert(reservation);
        return ResponseEntity.ok(ReservationResponse.of(id, reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable final Long id) {
        int count = reservationDao.delete(id);
        if (count == 0) {
            throw new IllegalArgumentException("[ERROR] 해당 id에 대한 예약 기록이 존재하지 않습니다.");
        }
        return ResponseEntity.ok().build();
    }
}
