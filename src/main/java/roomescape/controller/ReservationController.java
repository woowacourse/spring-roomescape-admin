package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationController(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> responses = reservationDao.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable long id) {
        Reservation reservation = reservationDao.findById(id);
        ReservationResponse response = ReservationResponse.from(reservation);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationCreateRequest request) {
        ReservationTime reservationTime = reservationTimeDao.findById(request.timeId());
        Reservation reservation = request.toReservation(reservationTime);

        Reservation saved = reservationDao.save(reservation);

        ReservationResponse response = ReservationResponse.from(saved);
        URI uri = URI.create("/reservations/" + response.id());
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable long id) {
        reservationDao.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
