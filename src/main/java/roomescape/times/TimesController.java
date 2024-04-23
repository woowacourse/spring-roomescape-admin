package roomescape.times;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.TimesRequest;
import roomescape.dto.ReservationTimeDto;
import roomescape.dto.response.TimesResponse;

import java.util.List;

@Controller
@RequestMapping("/times")
public class TimesController {

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @GetMapping
    @ResponseBody
    public List<TimesResponse> times() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeDto::toDomain)
                .map(TimesResponse::new)
                .toList();
    }

    @PostMapping
    @ResponseBody
    public TimesResponse addTimes(@RequestBody TimesRequest timesRequest) {
        ReservationTimeDto reservationTimeDto = new ReservationTimeDto(null, timesRequest.startAt());
        ReservationTimeDto newReservationTimeDto = reservationTimeRepository.add(reservationTimeDto);
        ReservationTime newReservationTime = newReservationTimeDto.toDomain();
        return new TimesResponse(newReservationTime);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void deleteTimes(@PathVariable Long id) {
        reservationTimeRepository.remove(id);
    }
}
