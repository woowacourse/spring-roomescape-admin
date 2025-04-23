package roomescape.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.CreateReservationTimeDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    public ReservationTimeResponseDto createTime(@RequestBody CreateReservationTimeDto request) {
        ReservationTime reservationTime = reservationTimeRepository.add(request);
        return ReservationTimeResponseDto.from(reservationTime);
    }
}
