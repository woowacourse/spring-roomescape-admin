package roomescape.repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class RoomescapeRepository {

    private final List<Reservation> reservations;
    private final AtomicLong index = new AtomicLong(1);

    public RoomescapeRepository(final List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Reservation> findAll() {
        return reservations;
    }

    public Reservation saveReservation(final Reservation reservation) {
        if (existsSameDateTime(reservation)) {
            throw new IllegalArgumentException("[ERROR] 이미 존재하는 예약시간입니다.");
        }
        Reservation saved = Reservation.toEntity(reservation, index.getAndIncrement());
        reservations.add(saved);
        return saved;
    }

    private boolean existsSameDateTime(final Reservation reservation) {
        return reservations.stream()
                .anyMatch(reservation::isSameDateTime);
    }
}
