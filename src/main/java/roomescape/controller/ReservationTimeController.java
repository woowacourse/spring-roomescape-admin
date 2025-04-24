package roomescape.controller;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.application.dto.request.ReservationTimeRequest;
import roomescape.application.dto.response.ReservationTimeResponse;
import roomescape.application.service.ReservationTimeService;

@RestController
@RequestMapping("times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public List<ReservationTimeResponse> getReservationTimes() {
        return reservationTimeService.getReservationTimes();
    }

    @PostMapping
    public ReservationTimeResponse saveReservationTime(@RequestBody ReservationTimeRequest request) {
        return reservationTimeService.saveReservationTime(request);
    }

    @DeleteMapping("{id}")
    public void deleteReservationTime(@PathVariable Long id, HttpServletResponse response) {
        boolean isDeleted = reservationTimeService.deleteReservationTime(id);

        if (!isDeleted) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
