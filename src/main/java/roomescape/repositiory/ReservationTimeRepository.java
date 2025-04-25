package roomescape.repositiory;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeRepository implements GeneralRepository<ReservationTime> {

    @Override
    public List findAll() {
        return List.of();
    }

    @Override
    public ReservationTime findById(Long id) {
        return null;
    }

    @Override
    public Long add(ReservationTime reservationTime) {
        return 0L;
    }

    @Override
    public void delete(Long id) {

    }
}
