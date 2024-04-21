package roomescape.domain;

import java.util.List;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTime reservation);

    void deleteBy(long id);

    List<ReservationTime> findAll();
}
