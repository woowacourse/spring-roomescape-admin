package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

public class CollectionReservationRepository implements ReservationRepository {
    private final List<Reservation> reservations;
    private final AtomicLong atomicLong;

    public CollectionReservationRepository() {
        this(new ArrayList<>());
    }

    public CollectionReservationRepository(List<Reservation> reservations) {
        this(reservations, new AtomicLong(0));
    }

    public CollectionReservationRepository(List<Reservation> reservations, AtomicLong atomicLong) {
        this.reservations = reservations;
        this.atomicLong = atomicLong;
    }

    @Override
    public Reservation save(ReservationRequest reservationRequest) {
        Reservation reservation = fromRequest(reservationRequest);
        reservations.add(reservation);
        return reservation;
    }

    private Reservation fromRequest(ReservationRequest reservationRequest) {
        long id = atomicLong.incrementAndGet();

        String name = reservationRequest.name();
        LocalDate date = reservationRequest.date();
        LocalTime time = reservationRequest.time();
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        return new Reservation(id, name, dateTime);
    }

    @Override
    public List<Reservation> findAll() {
        return reservations.stream()
                .sorted()
                .toList();
    }

    @Override
    public void delete(long reservationId) {
        reservations.stream()
                .filter(reservation -> reservation.hasSameId(reservationId))
                .findAny()
                .ifPresent(reservations::remove);
    }
}
