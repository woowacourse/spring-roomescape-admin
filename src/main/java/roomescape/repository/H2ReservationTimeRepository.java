package roomescape.repository;

import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTime;

@Repository
public class H2ReservationTimeRepository implements ReservationTimeRepository{
    @Override
    public ReservationTime save(ReservationTime time) {
        return null;
    }
}
