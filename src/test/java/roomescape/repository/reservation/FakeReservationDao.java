package roomescape.repository.reservation;

import java.util.ArrayList;
import java.util.List;
import roomescape.model.Reservation;

public class FakeReservationDao implements ReservationDao {

    private final List<Reservation> reservations = new ArrayList<>();
    private Long id = 1L;

    @Override
    public long save(final Reservation reservation) {
        reservation.setId(id);
        reservations.add(reservation);
        return reservation.getId();
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    @Override
    public void deleteById(final Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

}
