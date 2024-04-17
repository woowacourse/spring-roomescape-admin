package roomescape.storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

@Component
public class ReservationStorage {
    private final List<Reservation> reservations;
    private final AtomicLong atomicLong = new AtomicLong(0);

    public ReservationStorage() {
        this(new ArrayList<>());
    }

    public ReservationStorage(List<Reservation> reservations) {
        this.reservations = reservations;
    }

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

    public List<Reservation> findAllReservations() {
        return reservations.stream()
                .sorted()
                .toList();
    }

    public void delete(Long reservationId) {
        reservations.stream().filter(reservation -> reservation.getId() == reservationId)
                .findAny()
                .ifPresent(reservations::remove);
    }
}
