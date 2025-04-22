package roomescape.controller;

import java.time.Clock;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationName;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {
    private final Clock clock;
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationApiController(
            final Clock clock,
            final ReservationDao reservationDao,
            final ReservationTimeDao reservationTimeDao
    ) {
        this.clock = clock;
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> response = ReservationResponse.from(reservationDao.getReservations());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody final CreateReservationRequest createReservationRequest
    ) {
        ReservationTime reservationTime = reservationTimeDao.findTime(createReservationRequest.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약시간입니다."));
        ReservationName name = new ReservationName(createReservationRequest.name());
        ReservationDateTime dateTime = new ReservationDateTime(
                new ReservationDate(createReservationRequest.date()),
                reservationTime,
                clock
        );
        Reservation savedReservation = reservationDao.createReservation(name, dateTime);
        return ResponseEntity.ok(ReservationResponse.from(savedReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable final Long id) {
        reservationDao.deleteReservationById(id);
        return ResponseEntity.ok().build();
    }
}
