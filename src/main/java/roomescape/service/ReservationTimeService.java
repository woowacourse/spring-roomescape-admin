package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.exception.ReservationNotFoundException;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.ReservationTimeQueryingDao;
import roomescape.reservationtime.ReservationTimeRequest;
import roomescape.reservationtime.ReservationTimeUpdatingDao;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeQueryingDao reservationTimeQueryingDao;
    private final ReservationTimeUpdatingDao reservationTimeUpdatingDao;

    public ReservationTimeService(ReservationTimeQueryingDao reservationTimeQueryingDao, ReservationTimeUpdatingDao reservationTimeUpdatingDao) {
        this.reservationTimeQueryingDao = reservationTimeQueryingDao;
        this.reservationTimeUpdatingDao = reservationTimeUpdatingDao;
    }

    public List<ReservationTime> read() {
        return reservationTimeQueryingDao.findAllReservationTime();
    }

    @Transactional
    public ReservationTime create(ReservationTimeRequest reservationTimeReq) {
        Long generatedId = reservationTimeUpdatingDao.insert(reservationTimeReq);
        return reservationTimeQueryingDao.findReservationTimeById(generatedId);
    }

    public void update(ReservationTimeRequest newReservationTimeReq, Long id) {
        reservationTimeUpdatingDao.save(id, newReservationTimeReq);
    }

    public void delete(Long id) {
        int delete = reservationTimeUpdatingDao.delete(id);

        if (delete == 0) {
            throw new ReservationNotFoundException(id);
        }
    }
}
