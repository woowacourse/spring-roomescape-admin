package roomescape.repository;

import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> findAll() {
        return reservations;
    }

    @Override
    public Reservation findLast() {
        return reservations.getLast();
    }

    @Override
    public void add(Reservation reservationExcludeIndex) {
        Reservation reservation = Reservation.toEntity(reservationExcludeIndex, index.getAndIncrement());
        reservations.add(reservation);
    }

    @Override
    public void deleteById(Long id) {
        Reservation deleteReservation = findById(id);
        reservations.remove(deleteReservation);
    }

    private Reservation findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 id입니다."));
    }
}
