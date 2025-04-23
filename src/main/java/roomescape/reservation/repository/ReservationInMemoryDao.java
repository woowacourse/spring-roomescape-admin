package roomescape.reservation.repository;

import roomescape.reservation.entity.Reservation;
import roomescape.reservation.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationInMemoryDao implements ReservationDao {

    private final List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> findAll() {
        return reservations.stream()
                .toList();
    }

    public Optional<Reservation> findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.isSameId(id))
                .findFirst();
    }

    public Reservation save(Reservation reservation) {
        reservations.add(reservation);
        return reservation;
    }

    public void deleteById(Long id) {
        Reservation findReservation = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("삭제할 예약 엔티티가 존재하지 않습니다."));

        reservations.remove(findReservation);
    }
}
