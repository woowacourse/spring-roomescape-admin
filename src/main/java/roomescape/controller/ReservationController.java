package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.repository.ReservationJdbcDao;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationJdbcDao jdbcDao;

    public ReservationController(ReservationJdbcDao jdbcDao) {
        this.jdbcDao = jdbcDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getAll() {
        List<ReservationResponseDto> responseReservations = jdbcDao.findAll().stream()
                .map(ReservationResponseDto::from)
                .toList();

        return ResponseEntity.ok(responseReservations);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(
            @RequestBody ReservationRequestDto dto) {
        Reservation reservation = Reservation.create(dto);
        Long savedReservationId = jdbcDao.save(reservation);
        Reservation savedReservation = Reservation.create(savedReservationId,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
        return ResponseEntity.ok(ReservationResponseDto.from(savedReservation));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        jdbcDao.deleteById(id);
    }
}
