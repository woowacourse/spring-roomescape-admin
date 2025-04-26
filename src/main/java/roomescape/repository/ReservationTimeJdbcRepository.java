package roomescape.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.repository.dao.ReservationTimeH2Dao;

@Repository
@RequiredArgsConstructor
public class ReservationTimeJdbcRepository implements ReservationTimeRepository {

    private final ReservationTimeH2Dao reservationTimeH2Dao;

    @Override
    public List<ReservationTime> getAll() {
        return reservationTimeH2Dao.selectAll();
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        return reservationTimeH2Dao.insertAndGet(reservationTime);
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        return reservationTimeH2Dao.selectById(id);
    }

    @Override
    public void remove(ReservationTime reservationTime) {
        reservationTimeH2Dao.deleteById(reservationTime.getId());
    }
}
