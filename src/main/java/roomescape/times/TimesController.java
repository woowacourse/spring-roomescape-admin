package roomescape.times;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTimeDto;
import roomescape.dto.RequestTimes;
import roomescape.dto.ResponseTimes;

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
                .map(roomescape.dto.ReservationTimeDto::toDomain)
                .map(ResponseTimes::new)
                .toList();
    }

    @PostMapping
    @ResponseBody
    public ResponseTimes addTimes(@RequestBody RequestTimes requestTimes) {
        roomescape.dto.ReservationTimeDto reservationTimeDto = new roomescape.dto.ReservationTimeDto(null, requestTimes.startAt());
        Long id = reservationTimeRepository.add(reservationTimeDto);
        ReservationTimeDto newReservationTimeDto = new ReservationTimeDto(id, reservationTimeDto.startAt());
        return new ResponseTimes(newReservationTimeDto);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteTimes(@PathVariable Long id) {
        reservationTimeRepository.remove(id);
    }
}
