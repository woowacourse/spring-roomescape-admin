package roomescape.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import roomescape.domain.Reservation;

public class TestReservationDAOImpl implements ReservationDAO {

    final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAllReservation() {
        return Collections.unmodifiableList(reservations);
    }

    @Override
    public Long insertReservation(final Reservation reservation) {
        reservations.add(reservation);
        return (long) reservations.size();
    }

    @Override
    public int deleteReservationById(final Long id) {
        final long idMatchedCount = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .count();
        return (int) idMatchedCount;
    }

    @Override
    public boolean existsById(final Long id) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.getId().equals(id));
    }
}
