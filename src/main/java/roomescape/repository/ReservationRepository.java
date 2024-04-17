package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReservationRepository {

    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = Arrays.asList(
            Reservation.toEntity(index.getAndIncrement(), "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)),
            Reservation.toEntity(index.getAndIncrement(), "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0))
    );

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}
