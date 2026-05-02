package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.repository.ReservationTimeRepository;

@RestController
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping("/times")
    @ResponseStatus(HttpStatus.OK)
    public ReservationTime insertTimes(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        return reservationTimeRepository.insert(reservationTimeRequest);
    }

    @GetMapping("/times")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationTime> selectTimes() {
        return reservationTimeRepository.select();
    }

    @DeleteMapping("/times/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTimes(@PathVariable long id) {
        reservationTimeRepository.delete(id);
    }
}
