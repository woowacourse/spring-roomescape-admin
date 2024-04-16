package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.controller.dto.ReservationDto;
import roomescape.entity.Reservation;

@Controller
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/admin/reservation")
    public String reservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> reservations() {
        return reservations;
    }

    @PostMapping("/reservations")
    @ResponseBody
    public Reservation createReservation(@RequestBody ReservationDto reservationDto) {
        long id = index.incrementAndGet();
        String name = reservationDto.getName();
        LocalDate date = reservationDto.getDate();
        LocalTime time = reservationDto.getTime();

        Reservation newReservation = new Reservation(id, name, date, time);
        reservations.add(newReservation);
        return newReservation;
    }
}
