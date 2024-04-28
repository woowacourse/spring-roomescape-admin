package roomescape.domain;

import java.util.List;

public interface ReservationDao {

    List<Reservation> findAll();

    Reservation findById(long id);

    long save(Reservation reservation);

    DeletedCount deleteById(long id);

    void deleteAll();
}
