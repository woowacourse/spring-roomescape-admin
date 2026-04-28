package roomescape.reservation.repository;

import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(0);

    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    public Reservation save(String name, LocalDate date, LocalTime time) {
        Reservation reservation = new Reservation(index.incrementAndGet(), name, date, time);
        reservations.add(reservation);
        return reservation;
    }
}
