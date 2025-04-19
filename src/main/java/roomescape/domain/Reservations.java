package roomescape.domain;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class Reservations {
    private final List<Reservation> values = new ArrayList<>();
    private final AtomicLong increment = new AtomicLong(1);

    public Reservation add(final ReservationName name, final ReservationDateTime dateTime, Clock clock) {
        validateDateTime(dateTime, clock);
        Reservation newReservation = new Reservation(increment.getAndIncrement(), name, dateTime);
        values.add(newReservation);
        return newReservation;
    }

    public void deleteById(final Long id) {
        values.removeIf(reservation -> reservation.equalsById(id));
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(values);
    }

    private static void validateDateTime(final ReservationDateTime dateTime, final Clock clock) {
        if (!dateTime.isAfter(LocalDateTime.now(clock))) {
            throw new IllegalArgumentException("예약 일시는 오늘보다 이전일 수 없습니다.");
        }
    }
}
