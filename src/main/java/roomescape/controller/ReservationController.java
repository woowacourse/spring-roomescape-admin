package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Reservation;

@Controller
public class ReservationController {

    private static final int INITIAL_VALUE = 1;

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(INITIAL_VALUE);

    @ResponseBody
    @PostMapping("/reservations")
    public Reservation create(@RequestBody Reservation reservation) {
        Reservation createdReservation = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(createdReservation);

        return createdReservation;
    }
}
