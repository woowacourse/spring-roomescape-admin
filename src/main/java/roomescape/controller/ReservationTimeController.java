package roomescape.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.repository.ReservationTimeRepository;

@Controller
public class ReservationTimeController {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping("/times")
    @ResponseBody
    public TimeResponse create(@RequestBody TimeRequest request) {
        ReservationTime savedReservationTime = reservationTimeRepository.save(request.startAt());

        return TimeResponse.from(savedReservationTime);
    }

    @GetMapping("/times")
    @ResponseBody
    public List<TimeResponse> findAll() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(TimeResponse::from)
                .toList();
    }


    @DeleteMapping("/times/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
