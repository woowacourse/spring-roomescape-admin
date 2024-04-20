package roomescape.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationSaveRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;
import roomescape.repository.dto.ReservationSaveDto;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationController(final ReservationDao reservationDao, final ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        final List<ReservationResponse> reservationResponses = reservationDao.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> saveReservation(
            @RequestBody final ReservationSaveRequest reservationSaveRequest) {
        final Optional<ReservationTime> reservationTime = reservationTimeDao.findById(reservationSaveRequest.timeId());
        if (reservationTime.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ReservationSaveDto reservationSaveDto = ReservationSaveDto.of(reservationSaveRequest, reservationTime.get());
        final Reservation savedReservation = reservationDao.save(reservationSaveDto);
        return ResponseEntity.created(URI.create("/reservations/" + savedReservation.getId()))
                .body(ReservationResponse.from(savedReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(final @PathVariable("id") Long id) {
        final boolean isDeleted = reservationDao.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
