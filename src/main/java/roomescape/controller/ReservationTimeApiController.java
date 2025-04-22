package roomescape.controller;

import jakarta.validation.Valid;
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
public final class ReservationTimeApiController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeApiController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public List<ReservationTimeResponseDto> reservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(reservationTime -> new ReservationTimeResponseDto(
                        reservationTime.getId(),
                        reservationTime.getStartTime()))
                .toList();
    }

    @PostMapping
    public ReservationTimeResponseDto create(@Valid @RequestBody ReservationTimeRequestDto reservationTimeRequestDto) {
        ReservationTime reservationTime = reservationTimeRepository.add(new ReservationTime(reservationTimeRequestDto.startAt()));
        return new ReservationTimeResponseDto(reservationTime.getId(), reservationTime.getStartTime());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        reservationTimeRepository.removeById(id);
    }
}
