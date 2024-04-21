package roomescape.domain;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    void deleteBy(long id);

    List<Reservation> findAll();
}
