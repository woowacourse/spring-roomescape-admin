package roomescape.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.model.Reservation;

public class MemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations;

    private AtomicLong index = new AtomicLong(0);

    public MemoryReservationRepository() {
        reservations = new ArrayList<>();
    }

    @Override
    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    @Override
    public Reservation save(Reservation reservation) {
        long currentIndex = index.incrementAndGet();
        Reservation reservationEntity = reservation.assignId(currentIndex);
        reservations.add(reservationEntity);
        return reservationEntity;
    }

    @Override
    public void deleteById(Long id) {
        Optional<Reservation> findReservation = reservations.stream()
                .filter(reservation -> reservation.sameId(id))
                .findAny();
        findReservation.ifPresent(reservations::remove);
    }
}
