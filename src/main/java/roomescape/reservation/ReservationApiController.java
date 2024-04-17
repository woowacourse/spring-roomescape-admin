package roomescape.reservation;

import java.lang.reflect.Member;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationApiController {
    private static final int START_INDEX = 1;

    private List<Reservation> reservations;
    private AtomicLong index;

    public ReservationApiController() {
        index = new AtomicLong(START_INDEX);
        reservations = List.of(
            new Reservation(index.getAndIncrement(),"브라운", LocalDate.of(2024,04,01), LocalTime.of(10,00)),
            new Reservation(index.getAndIncrement(),"솔라", LocalDate.of(2024,04,01), LocalTime.of(11,00)),
            new Reservation(index.getAndIncrement(),"부리", LocalDate.of(2024,04,02), LocalTime.of(14,00))
        );
    }

    @GetMapping("/reservations")
    public List<Reservation> findAllReservations() {
        return reservations;
    }
}
