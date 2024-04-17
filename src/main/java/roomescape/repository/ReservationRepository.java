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
            new Reservation(index.getAndIncrement(), "브라운", LocalDate.parse("2023-01-01"), LocalTime.parse("10:00")),
            new Reservation(index.getAndIncrement(), "브라운", LocalDate.parse("2023-01-02"), LocalTime.parse("11:00"))
    ));

    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    public Reservation save(Reservation reservation) {
        Reservation savedReservation = new Reservation(index.getAndIncrement(), reservation.getName(), reservation.getDate(), reservation.getTime());
        reservations.add(savedReservation);
        return savedReservation;
    }

    public void deleteById(Long id) {
        Reservation reservation = findById(id);
        reservations.remove(reservation);
    }

    private Reservation findById(Long id) {
        return reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 아아디입니다."));
    }
}
