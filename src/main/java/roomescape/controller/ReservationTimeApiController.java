package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.dto.time.ReservationTimeRequestDto;
import roomescape.dto.time.ReservationTimeResponseDto;
import roomescape.repository.time.JdbcReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class ReservationTimeApiController {

    private final JdbcReservationTimeRepository jdbcReservationTimeRepository;

    public ReservationTimeApiController(JdbcReservationTimeRepository jdbcReservationTimeRepository) {
        this.jdbcReservationTimeRepository = jdbcReservationTimeRepository;
    }

    @GetMapping
    public List<ReservationTimeResponseDto> getReservationTimes() {
        return jdbcReservationTimeRepository.findAllReservationTimes()
                .stream()
                .map(ReservationTimeResponseDto::new)
                .toList();
    }

    @PostMapping
    public ReservationTimeResponseDto createReservationTime(@RequestBody ReservationTimeRequestDto requestDto) {
        ReservationTime reservationTime = requestDto.toReservationTime();
        Long id = jdbcReservationTimeRepository.insertReservationTime(reservationTime);
        return new ReservationTimeResponseDto(id, reservationTime.getStartAt());
    }

    @DeleteMapping("/{id}")
    public void deleteReservationTime(@PathVariable long id) {
        jdbcReservationTimeRepository.deleteReservationTimeById(id);
    }
}
