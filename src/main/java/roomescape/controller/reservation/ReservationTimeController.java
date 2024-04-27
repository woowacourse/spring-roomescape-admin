package roomescape.controller.reservation;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.request.ReservationTimeAddRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ReservationTimeResponse addTime(@RequestBody ReservationTimeAddRequest reservationTimeAddRequest) {
        return reservationTimeService.addTime(reservationTimeAddRequest);
    }

    @GetMapping
    public List<ReservationTimeResponse> findTimes() {
        return reservationTimeService.findTimes();
    }

    @GetMapping("/{id}")
    public ReservationTimeResponse getTime(@PathVariable Long id) {
        return reservationTimeService.getTime(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTime(@PathVariable Long id) {
        reservationTimeService.deleteTime(id);
    }
}
