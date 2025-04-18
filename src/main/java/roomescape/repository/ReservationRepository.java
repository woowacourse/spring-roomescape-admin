package roomescape.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.response.ReservationResponse;

@Repository
public class ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public List<ReservationResponse> findAll() {
        return createReservationResponses();
    }

    private List<ReservationResponse> createReservationResponses() {
        return reservations.stream()
                .map(this::createReservationResponse)
                .collect(Collectors.toList());
    }

    private ReservationResponse createReservationResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
    }

    public Long create(final String name, final LocalDate date, final LocalTime time) {
        Reservation reservation = new Reservation(index.getAndIncrement(),
                name,
                date,
                time
        );
        reservations.add(reservation);
        return reservation.getId();
    }

    public void delete(final Long id) {
        Reservation reservation = reservations.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(reservation);
    }
}
