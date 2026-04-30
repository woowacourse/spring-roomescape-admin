package roomescape.time.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.ReservationTimeJdbcDao;

@Service
public class ReservationTimeService {

    private final ReservationTimeJdbcDao jdbcDao;

    public ReservationTimeService(ReservationTimeJdbcDao jdbcDao) {
        this.jdbcDao = jdbcDao;
    }

    public ReservationTime save(ReservationTime reservationTime) {
        Long savedId = jdbcDao.save(reservationTime);
        return ReservationTime.create(savedId, reservationTime.getStartAt());
    }

    public List<ReservationTime> findAll() {
        return jdbcDao.findAll();
    }

    public void deleteById(Long id) {
        jdbcDao.deleteById(id);
    }
}
