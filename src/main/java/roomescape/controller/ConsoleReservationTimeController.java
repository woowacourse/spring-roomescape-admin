package roomescape.controller;


import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Controller;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

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

    public void add(final String read) {
        reservationTimeService.create(new ReservationTimeRequest(LocalTime.parse(read)));
    }

    public void delete(final long read) {
        reservationTimeService.deleteById(read);
    }
}
