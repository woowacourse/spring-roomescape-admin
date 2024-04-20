package roomescape.domain;

import java.util.List;

public interface ReservationTimeRepository {

    ReservationTime saveOne(ReservationTime reservation);

    void deleteBy(Long id);

    ReservationTime findById(Long id);

    List<ReservationTime> findAll();
}
