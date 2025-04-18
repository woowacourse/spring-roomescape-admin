package roomescape.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationDto;

@Component
public class Reservations {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public Long add(final ReservationDto reservationDto) {
        ReservationDateTime reservationDateTime = new ReservationDateTime(
                LocalDateTime.of(reservationDto.date(), reservationDto.time())
        );

        Long id = index.getAndIncrement();
        Reservation newReservation = new Reservation(
                id,
                reservationDto.name(),
                reservationDateTime);

        reservations.add(newReservation);
        return id;
    }

    public void removeById(Long id) {
        Reservation removeReservation = findById(id);
        reservations.remove(removeReservation);
    }

    public Reservation findById(Long id) {
        return reservations.stream()
                .filter(reservation -> Objects.equals(reservation.getId(), id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 찾으려는 id 값이 없습니다."));
    }

    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }
}
