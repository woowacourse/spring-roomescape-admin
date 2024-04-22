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
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.dto.ReservationTimeCreateRequestDto;
import roomescape.dto.ReservationTimeResponseDto;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponseDto>> readAll() {
        List<ReservationTime> allReservationTimes = reservationTimeDao.findAll();
        List<ReservationTimeResponseDto> results = allReservationTimes.stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> create(@RequestBody ReservationTimeCreateRequestDto requestDto) {
        ReservationTime reservationTime = requestDto.toDomain();
        long id = reservationTimeDao.add(ReservationTimeCreateRequestDto.from(reservationTime));
        ReservationTime result = reservationTimeDao.findById(id);
        return ResponseEntity.created(URI.create("/times/" + id))
                .body(ReservationTimeResponseDto.from(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeDao.delete(id);
        return ResponseEntity.noContent().build();
    }
}
