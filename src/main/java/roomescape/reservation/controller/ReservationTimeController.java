package roomescape.reservation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.ReservationTimeRequestDto;
import roomescape.reservation.dto.ReservationTimeResponseDto;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.repository.ReservationTimeInMemoryRepository;

@RestController
public class ReservationTimeController {

    private final ReservationTimeInMemoryRepository repository;

    public ReservationTimeController(ReservationTimeInMemoryRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/times")
    public ReservationTimeResponseDto add(@RequestBody ReservationTimeRequestDto requestDto) {
        ReservationTime reservationTime = new ReservationTime(requestDto.startAt());
        ReservationTime savedReservationTime = repository.save(reservationTime);
        return ReservationTimeResponseDto.toDto(savedReservationTime);
    }
}
