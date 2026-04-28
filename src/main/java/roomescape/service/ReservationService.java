package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;

@Service
public class ReservationService {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public Reservation add(ReservationRequest request) {
        LocalDate date = LocalDate.parse(request.date());
        LocalTime time = LocalTime.parse(request.time());

        Reservation reservation = new Reservation(
            nextId.getAndIncrement(),
            request.name(),
            date,
            time);

        reservations.add(reservation);

        return reservation;
    }
}
