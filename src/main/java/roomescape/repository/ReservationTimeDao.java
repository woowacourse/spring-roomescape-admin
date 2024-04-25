package roomescape.repository;

import java.util.List;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeAddRequest;

public interface ReservationTimeDao {
    List<ReservationTime> findAll();

    ReservationTime findById(Long id);

    ReservationTime insert(ReservationTimeAddRequest reservationTimeAddRequest);

    void deleteById(Long id);
}
