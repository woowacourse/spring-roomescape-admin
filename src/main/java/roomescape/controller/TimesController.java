package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeDto;
import roomescape.dto.request.TimesRequest;
import roomescape.dto.response.TimesResponse;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimesController {

    private final ReservationTimeRepository reservationTimeRepository;

    public TimesController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    public List<TimesResponse> times() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeDto::toDomain)
                .map(TimesResponse::new)
                .toList();
    }

    @PostMapping
    public TimesResponse addTimes(@RequestBody TimesRequest timesRequest) {
        ReservationTimeDto reservationTimeDto = new ReservationTimeDto(null, timesRequest.startAt());
        ReservationTimeDto newReservationTimeDto = reservationTimeRepository.add(reservationTimeDto);
        ReservationTime newReservationTime = newReservationTimeDto.toDomain();
        return new TimesResponse(newReservationTime);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public int deleteTimes(@PathVariable Long id) {
        return reservationTimeRepository.remove(id);
    }
}
