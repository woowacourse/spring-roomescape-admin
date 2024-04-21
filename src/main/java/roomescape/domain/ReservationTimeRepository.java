package roomescape.domain;

import java.util.List;

public interface ReservationTimeRepository {

    ReservationTime saveOne(ReservationTime reservation);

    void deleteBy(long id);

    List<ReservationTime> findAll();
}
