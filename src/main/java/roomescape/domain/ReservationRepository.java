package roomescape.domain;

import java.util.List;

public interface ReservationRepository {

    Reservation saveOne(Reservation reservation);

    void deleteBy(long id);

    List<Reservation> findAll();
}
