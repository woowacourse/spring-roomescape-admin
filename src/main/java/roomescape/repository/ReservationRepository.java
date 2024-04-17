package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {

    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>(List.of(
            Reservation.toEntity(index.getAndIncrement(), "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)),
            Reservation.toEntity(index.getAndIncrement(), "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0))
    ));

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    public Reservation addReservation(Reservation requestReservation) {
        Reservation reservation = Reservation.toEntity(index.getAndIncrement(), requestReservation.getName(), requestReservation.getDate(), requestReservation.getTime());
        reservations.add(reservation);
        return reservation;
    }
}
