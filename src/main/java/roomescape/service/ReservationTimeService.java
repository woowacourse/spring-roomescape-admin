package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.entity.ReservationTime;
import roomescape.dao.JdbcReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final JdbcReservationTimeDao jdbcReservationTimeDao;

    public ReservationTimeService(JdbcReservationTimeDao jdbcReservationTimeDao) {
        this.jdbcReservationTimeDao = jdbcReservationTimeDao;
    }

    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> reservationTimes = jdbcReservationTimeDao.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResponse::of)
                .toList();
    }

    public ReservationTimeResponse save(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toReservationTime();
        long id = jdbcReservationTimeDao.save(reservationTime);
        return findById(id);
    }

    private ReservationTimeResponse findById(long id) {
        ReservationTime reservationTime = jdbcReservationTimeDao.findById(id);
        return ReservationTimeResponse.of(reservationTime);
    }

    public void deleteById(long id) {
        int isDelete = jdbcReservationTimeDao.deleteById(id);
        if (isDelete < 1) {
            throw new IllegalArgumentException("해당 id는 존재하지 않습니다.");
        }
    }
}
