package roomescape.repository;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations);
    }

    @Override
    public Reservation insertAndGet(Reservation reservationExcludeIndex) {
        Reservation reservation = Reservation.toEntity(reservationExcludeIndex, index.getAndIncrement());
        reservations.add(reservation);
        return reservation;
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

    @Override
    public boolean existByDateAndTime(LocalDate date, LocalTime time) {
        return false;
    }
}
