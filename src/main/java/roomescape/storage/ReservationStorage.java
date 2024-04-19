package roomescape.storage;

import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ReservationStorage {
    private final List<Reservation> reservations;
    private final AtomicLong atomicLong;

    public ReservationStorage() {
        this((new CopyOnWriteArrayList<>()));
    }

    public ReservationStorage(List<Reservation> reservations) {
        this(reservations, new AtomicLong(0));
    }

    public ReservationStorage(List<Reservation> reservations, AtomicLong atomicLong) {
        this.reservations = reservations;
        this.atomicLong = atomicLong;
    }

    public Reservation save(Reservation reservation) {
        reservation.setId(atomicLong.incrementAndGet());
        reservations.add(reservation);
        return reservation;
    }

    public List<Reservation> findAllReservations() {
        return reservations.stream()
                .sorted(Reservation::compareDatetime)
                .toList();
    }

    public void delete(long reservationId) {
        reservations.stream()
                .filter(reservation -> reservation.hasSameId(reservationId))
                .findAny()
                .ifPresent(reservations::remove);
    }
}
