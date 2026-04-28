package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ReservationRepository {

    private final List<Reservation> reservations;
    private final AtomicLong idGenerator;

    public ReservationRepository() {
        reservations = new ArrayList<>();
        idGenerator = new AtomicLong(0);
    }

    public List<Reservation> findAll() {
        return List.copyOf(reservations);
    }

    public Reservation save(String name, LocalDate reservationDate, LocalTime reservationTime) {
        Reservation reservation = new Reservation(idGenerator.incrementAndGet(), name, reservationDate, reservationTime);
        reservations.add(reservation);
        return reservation;
    }
}
