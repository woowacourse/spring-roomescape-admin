package roomescape.respository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservationtime.ReservationTime;

@Repository
public interface ReservationTimeRepository {

    List<ReservationTime> findAll();

    ReservationTime findById(long id);

    ReservationTime add(ReservationTime reservationTime);

    Boolean exist(long id);

    void delete(long id);
}
