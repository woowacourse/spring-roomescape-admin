package roomescape.console.controller;


import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Controller;
import roomescape.general.dto.ReservationTimeRequest;
import roomescape.general.dto.ReservationTimeResponse;
import roomescape.general.service.ReservationTimeService;

@Controller
public class ConsoleReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ConsoleReservationTimeController(final ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeService.findAll().stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public void add(final String command) {
        reservationTimeService.create(new ReservationTimeRequest(LocalTime.parse(command)));
    }

    public void delete(final long command) {
        reservationTimeService.deleteById(command);
    }
}
