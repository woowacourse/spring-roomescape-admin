package roomescape.domain;

import java.util.List;

public interface ReservationTimeDao {

    List<ReservationTime> findAll();

    ReservationTime findById(long id);

    long save(ReservationTime reservationTime);

    DeletedCount deleteById(long id);

    void deleteAll();
}
