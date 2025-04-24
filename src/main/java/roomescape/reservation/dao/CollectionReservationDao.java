package roomescape.reservation.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.common.Dao;
import roomescape.reservation.Reservation;

public class CollectionReservationDao implements Dao<Reservation> {
    private final AtomicLong index = new AtomicLong(0);
    private final List<Reservation> reservations;

    public CollectionReservationDao() {
        this.reservations = new ArrayList<>();
    }

    public CollectionReservationDao(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public Reservation add(Reservation reservation) {
        Reservation newReservation = new Reservation(
                index.incrementAndGet(),
                reservation.name(),
                reservation.date(),
                reservation.time());
        reservations.add(newReservation);
        return newReservation;
    }

    @Override
    public Reservation getById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameId(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 id 입니다."));
    }

    @Override
    public List<Reservation> getAll() {
        return Collections.unmodifiableList(reservations);
    }

    @Override
    public void deleteById(Long id) {
        reservations.remove(getById(id));
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        CollectionReservationDao that = (CollectionReservationDao) other;
        return Objects.equals(reservations, that.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reservations);
    }
}
