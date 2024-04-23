package roomescape.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.service.dto.ReservationCreationDto;

@Repository
public class MemoryReservationDao implements ReservationDao {
    private final List<Reservation> reservations;
    private final AtomicLong index;

    public MemoryReservationDao() {
        this.reservations = new ArrayList<>();
        this.index = new AtomicLong(0);
    }

    @Override
    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    @Override
    public Reservation add(ReservationCreationDto request) {
        Reservation newReservation = new Reservation(index.incrementAndGet(), request.name(),
                request.date(), request.time());
        reservations.add(newReservation);
        return newReservation;
    }

    @Override
    public void delete(Long id) {
        reservations.stream()
                .filter(reservation -> reservation.hasId(id))
                .findFirst()
                .ifPresent(reservations::remove);
    }

    @Override
    public void deleteAll() {
        reservations.clear();
    }

    @Override
    public boolean isExist(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.hasId(id))
                .findFirst()
                .map(notUsed -> Boolean.TRUE)
                .orElse(Boolean.FALSE);
    }
}
