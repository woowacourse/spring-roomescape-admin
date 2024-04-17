package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {

    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>(List.of(
            Reservation.toEntity(index.getAndIncrement(), "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)),
            Reservation.toEntity(index.getAndIncrement(), "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0))
    ));

    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    public Reservation save(Reservation requestReservation) {
        Reservation reservation = Reservation.toEntity(index.getAndIncrement(), requestReservation.getName(), requestReservation.getDate(), requestReservation.getTime());
        reservations.add(reservation);
        return reservation;
    }

    public void deleteById(Long id) {
        Reservation reservation = findById(id);
        reservations.remove(reservation);
    }

    private Reservation findById(Long id) {
        return reservations.stream()
                .filter(e -> e.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 아아디입니다."));
    }
}
