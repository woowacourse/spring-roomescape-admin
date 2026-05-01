package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.TimeRequest;
import roomescape.controller.dto.TimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@RestController
public class ReservationTimeController {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping("/times")
    public TimeResponse create(@RequestBody TimeRequest request) {
        ReservationTime savedReservationTime = reservationTimeRepository.save(request.startAt());

        return TimeResponse.from(savedReservationTime);
    }

    @GetMapping("/times")
    public List<TimeResponse> findAll() {
        return reservationTimeRepository.findAll()
                .stream()
                .map(TimeResponse::from)
                .toList();
    }


    @DeleteMapping("/times/{id}")
    public void delete(@PathVariable Long id) {
        reservationTimeRepository.deleteById(id);
    }
}
