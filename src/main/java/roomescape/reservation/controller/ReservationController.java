package roomescape.reservation.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.repository.ReservationJdbcDao;
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.ReservationTimeJdbcDao;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationJdbcDao reservationJdbcDao;
    private final ReservationTimeJdbcDao reservationTimeJdbcDao;

    public ReservationController(ReservationJdbcDao reservationJdbcDao, ReservationTimeJdbcDao reservationTimeJdbcDao) {
        this.reservationJdbcDao = reservationJdbcDao;
        this.reservationTimeJdbcDao = reservationTimeJdbcDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAll() {
        List<ReservationResponseDto> responseReservations = reservationJdbcDao.findAll().stream()
                .map(ReservationResponseDto::from)
                .toList();

        return ResponseEntity.ok(responseReservations);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(@RequestBody ReservationRequestDto dto) {
        ReservationTime reservationTime = reservationTimeJdbcDao.findById(dto.timeId());
        Reservation reservation = Reservation.create(dto, reservationTime);

        Long savedReservationId = reservationJdbcDao.save(reservation);

        Reservation savedReservation = Reservation.create(savedReservationId,
                reservation.getName(),
                reservation.getDate(),
                reservation.getReservationTime());

        return ResponseEntity.ok(ReservationResponseDto.from(savedReservation));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationJdbcDao.deleteById(id);
    }
}
