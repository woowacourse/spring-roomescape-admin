package roomescape.console.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Controller;
import roomescape.general.dto.ReservationRequest;
import roomescape.general.dto.ReservationResponse;
import roomescape.general.service.ReservationService;

@Controller
public class ConsoleReservationController {
    private final ReservationService reservationService;

    public ConsoleReservationController(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public List<ReservationResponse> findAll() {
        return reservationService.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void add(final List<String> read) {
        String name = read.get(0);
        LocalDate date = LocalDate.parse(read.get(1));
        Long timeId = Long.parseLong(read.get(2));
        reservationService.create(new ReservationRequest(name, date, timeId));
    }

    public void delete(final long read) {
        reservationService.deleteById(read);
    }
}
