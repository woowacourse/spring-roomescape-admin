package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Controller
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @PostMapping("/reservations")
    @ResponseBody
    public ReservationResponse create(@RequestBody ReservationRequest request) {
        Long id = index.getAndIncrement();

        Reservation reservation = new Reservation(
                id,
                new Name(request.name()),
                new ReservationDate(request.date()),
                new ReservationTime(request.time())
        );

        reservations.add(reservation);

        return ReservationResponse.from(reservation);
    }
}
