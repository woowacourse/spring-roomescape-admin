package roomescape.controller;

import java.net.URI;
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
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.dto.ReservationCreateRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeResponseDto;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationController(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> readAll() {
        List<Reservation> reservations = reservationDao.findAll();
        List<ReservationResponseDto> results = reservations.stream()
                .map(reservation -> {
                    ReservationTime reservationTime = reservation.getReservationTime();
                    ReservationTimeResponseDto timeResponseDto = ReservationTimeResponseDto.from(reservationTime);
                    return ReservationResponseDto.of(reservation, timeResponseDto);
                })
                .toList();
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<ReservationCreateRequestDto> create(@RequestBody ReservationCreateRequestDto requestDto) {
        ReservationTime reservationTime = reservationTimeDao.findById(requestDto.getTimeId());
        Reservation reservation = requestDto.toDomain(reservationTime);
        ReservationCreateRequestDto createRequestDto = ReservationCreateRequestDto.of(reservation,
                reservationTime);
        long id = reservationDao.add(createRequestDto);
        Reservation result = reservationDao.findById(id);
        return ResponseEntity.created(URI.create("/reservations"))
                .body(ReservationCreateRequestDto.of(result, reservationTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationDao.delete(id);
        return ResponseEntity.noContent().build();
    }
}
