package roomescape.database;

import java.util.ArrayList;
import java.util.List;
import roomescape.domain.Reservation;

public class ReservationDatabaseImpl implements ReservationDatabase {

    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public Reservation findById(Long id) {
        return reservations.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 예약 번호입니다:" + id));
    }

    @Override
    public Reservation add(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }
}
