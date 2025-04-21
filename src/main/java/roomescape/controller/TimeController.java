package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeDto;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public TimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public List<ReservationTimeDto> getAllTime() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeDto::from)
                .toList();
    }
}
