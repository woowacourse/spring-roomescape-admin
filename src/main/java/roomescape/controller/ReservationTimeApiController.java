package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class ReservationTimeApiController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeApiController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping()
    public List<ReservationTimeResponseDto> times() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    @PostMapping
    public ReservationTimeResponseDto add(@RequestBody ReservationTimeRequestDto reservationTimeRequestDto) {
        Long id = reservationTimeRepository.add(reservationTimeRequestDto.toReservationTime());
        ReservationTime reservationTime = reservationTimeRepository.findById(id);
        return ReservationTimeResponseDto.from(reservationTime);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationTimeRepository.removeById(id);
    }
}
