package roomescape.repository;

import java.util.ArrayList;
import java.util.List;
import roomescape.domain.Reservation;

public class ReservationRepository {

    private final List<Reservation> reservations;

    public ReservationRepository() {
        this.reservations = new ArrayList<>();
    }


    public List<Reservation> findAll() {
        return reservations;
    }

    public void save(final Reservation reservation) {
        reservations.add(reservation);
    }

    public void delete(final Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        reservations.remove(reservation);
    }
}
