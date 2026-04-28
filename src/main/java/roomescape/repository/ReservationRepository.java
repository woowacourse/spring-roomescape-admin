package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.exception.ReservationNotFoundException;
import roomescape.model.Reservation;

@Repository
public class ReservationRepository {
    private final List<Reservation> reservations;
    private final AtomicLong idGenerator = new AtomicLong();

    public ReservationRepository() {
        this.reservations = new ArrayList<>();
        this.idGenerator.set(1L);
    }

    public List<Reservation> findAll() {
        return reservations;
    }

    public Reservation save(String name, String date, String time) {
        Reservation reservation = new Reservation(idGenerator.getAndIncrement(), name, date, time);
        reservations.add(reservation);
        return reservation;
    }

    public void delete(Long id) {
        reservations.remove(findById(id));
    }

    private Reservation findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

}
