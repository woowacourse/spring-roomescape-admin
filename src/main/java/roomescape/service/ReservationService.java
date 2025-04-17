package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.model.Reservation;

@Service
public class ReservationService {

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(1);

    public boolean addReservation(Reservation reservation) {
        return reservations.add(reservation);
    }

    public Reservation deleteReservation(long id) {
        Reservation oldReservation = reservations.stream().filter(reservation -> reservation.getId() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 ID 없음"));
        reservations.remove(oldReservation);
        return oldReservation;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public Long getIndexAndIncrement() {
        return index.getAndIncrement();
    }
}
