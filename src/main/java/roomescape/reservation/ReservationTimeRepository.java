package roomescape.reservation;

import java.util.List;

public interface ReservationTimeRepository {

    ReservationTime save(ReservationTimeRequest request);

    List<ReservationTime> findAll();

    void deleteById(Long id);

}
