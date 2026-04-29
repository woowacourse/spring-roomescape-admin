package roomescape.time.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.ReservationTimeRequestDto;
import roomescape.time.dto.ReservationTimeResponseDto;
import roomescape.time.repository.ReservationTimeJdbcDao;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeJdbcDao jdbcDao;

    public ReservationTimeController(ReservationTimeJdbcDao jdbcDao) {
        this.jdbcDao = jdbcDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponseDto>> getAllReservationTime() {
        List<ReservationTimeResponseDto> response = jdbcDao.findAll().stream()
                .map(ReservationTimeResponseDto::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponseDto> create(@RequestBody ReservationTimeRequestDto dto) {
        ReservationTime reservationTime = ReservationTime.create(dto);
        Long savedId = jdbcDao.save(reservationTime);
        ReservationTime savedReservationTime = ReservationTime.create(savedId, reservationTime.getStartAt());

        return ResponseEntity.ok(ReservationTimeResponseDto.from(savedReservationTime));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        jdbcDao.deleteById(id);
    }
}
