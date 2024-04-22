package roomescape.times;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.RequestTimes;
import roomescape.dto.ReservationTimeDto;
import roomescape.dto.response.ResponseTimes;

import java.util.List;

@Controller
@RequestMapping("/times")
public class TimesController {

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @GetMapping
    @ResponseBody
    public List<ResponseTimes> times() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeDto::toDomain)
                .map(ResponseTimes::new)
                .toList();
    }

    @PostMapping
    @ResponseBody
    public ResponseTimes addTimes(@RequestBody RequestTimes requestTimes) {
        ReservationTimeDto reservationTimeDto = new ReservationTimeDto(null, requestTimes.startAt());
        Long id = reservationTimeRepository.add(reservationTimeDto);
        ReservationTimeDto newReservationTimeDto = new ReservationTimeDto(id, reservationTimeDto.startAt());
        ReservationTime newReservationTime = newReservationTimeDto.toDomain();
        return new ResponseTimes(newReservationTime);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void deleteTimes(@PathVariable Long id) {
        reservationTimeRepository.remove(id);
    }
}
