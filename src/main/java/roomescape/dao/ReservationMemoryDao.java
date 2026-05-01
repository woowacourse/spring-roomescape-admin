package roomescape.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

public class ReservationMemoryDao implements ReservationDao {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> selectReservations() {
        return List.copyOf(reservations);
    }

    @Override
    public Reservation insertReservation(ReservationRequest request, ReservationTime time) {
        Reservation newReservation =  new Reservation(index.getAndIncrement(), request.getName(),request.getDate(), time);
        reservations.add(newReservation);
        return newReservation;
    }

    @Override
    public void deleteReservation(long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        reservations.remove(reservation);
    }
}
