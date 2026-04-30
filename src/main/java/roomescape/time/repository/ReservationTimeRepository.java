package roomescape.time.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.ReservationTime;
import roomescape.time.mapper.ReservationTimeMapper;
import roomescape.time.repository.dao.ReservationTimeDao;

@Repository
public class ReservationTimeRepository {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeRepository(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime save(ReservationTime reservationTime) {
        Long id = reservationTimeDao.insert(ReservationTimeMapper.toReservationTimeEntity(reservationTime));
        return new ReservationTime(id, reservationTime.getStartAt());
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.selectAll().stream()
                .map(ReservationTimeMapper::toReservationTime)
                .toList();
    }

    public void deleteById(Long id) {
        int deletedCount = reservationTimeDao.delete(id);

        if (deletedCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 ID입니다");
        }
    }
}
