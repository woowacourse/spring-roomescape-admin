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
    private final AtomicLong atomicLong;

    public ReservationStorage() {
        this(new ArrayList<>());
    }

    public ReservationStorage(List<Reservation> reservations) {
        this(reservations, new AtomicLong(0));
    }

    public ReservationStorage(List<Reservation> reservations, AtomicLong atomicLong) {
        this.reservations = reservations;
        this.atomicLong = atomicLong;
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

    //Todo 메서드 제거하고, 외부에서 내부 요소를 주입받을 수 있게 하는 것은 어떤가? TestConfig 에서 하면 될 듯
    public void removeAll() {
        atomicLong.set(0);
        reservations.clear();
    }
}
