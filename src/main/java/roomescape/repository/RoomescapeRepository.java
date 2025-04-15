package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;

@Repository
public class RoomescapeRepository {

    private final List<Reservation> reservations;

    public RoomescapeRepository(final List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Reservation> findAll() {
        return reservations;
    }
}
