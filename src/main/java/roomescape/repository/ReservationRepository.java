package roomescape.repository;

import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();

    public void save(Reservation reservation) {
        reservations.add(reservation);
    }

    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    public void deleteById(Long id) {
        Reservation target = reservations.stream()
                .filter(reservation -> reservation.hasSameId(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 예약이 없습니다."));
        reservations.remove(target);
    }
}
