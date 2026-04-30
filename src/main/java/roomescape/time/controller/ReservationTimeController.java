package roomescape.time.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.time.repository.JdbcReservationTimeRepository;
import roomescape.time.dto.TimeRequestDto;
import roomescape.time.entity.ReservationTime;

import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final JdbcReservationTimeRepository jdbcReservationTimeRepository;

    public ReservationTimeController(JdbcReservationTimeRepository jdbcReservationTimeRepository) {
        this.jdbcReservationTimeRepository = jdbcReservationTimeRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> readAll() {
        return ResponseEntity.ok(jdbcReservationTimeRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<ReservationTime> create(@RequestBody TimeRequestDto requestDto) {
        return ResponseEntity.ok(jdbcReservationTimeRepository.save(requestDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        jdbcReservationTimeRepository.delete(id);
    }
}
