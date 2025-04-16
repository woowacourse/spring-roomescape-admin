package roomescape.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.dto.CreateReservationDto;
import roomescape.entity.Reservation;
import roomescape.exception.InvalidReservationException;

@Repository
public class ReservationRepository {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public List<Reservation> findAll() {
        return Collections.unmodifiableList(reservations);
    }

    public Reservation add(CreateReservationDto createReservationDto) {
        Reservation reservation = createReservationDto.toEntity(index.getAndIncrement());
        reservations.add(reservation);
        return reservation;
    }

    public void deleteById(Long id) {
        Reservation reservation = reservations.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(InvalidReservationException::new);

        reservations.remove(reservation);
    }
}
