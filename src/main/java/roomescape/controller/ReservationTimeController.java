package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import roomescape.controller.dto.ReservationTimeRegisterDto;
import roomescape.controller.dto.ReservationTimeResponseDto;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    @ResponseBody
    public ReservationTimeResponseDto registerReservationTime(
            @RequestBody final ReservationTimeRegisterDto reservationTimeRegisterDto) {
        ReservationTime reservationTime = reservationTimeRegisterDto.toReservationTime();
        reservationTimeRepository.save(reservationTime);
        return new ReservationTimeResponseDto(reservationTime);
    }

    @GetMapping
    @ResponseBody
    public List<ReservationTimeResponseDto> getReservationTimes() {
        return reservationTimeRepository.findAll().stream()
                .map(ReservationTimeResponseDto::new)
                .toList();
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") final Long id) {
        try {
            reservationTimeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }
}
