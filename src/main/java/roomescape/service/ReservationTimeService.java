package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.exception.ReservationNotFoundException;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.ReservationTimeQueryingDao;
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

    public ReservationTime create(ReservationTime reservationTime) {
        Long generatedId = reservationTimeUpdatingDao.insert(reservationTime);
        return reservationTimeQueryingDao.findReservationTimeById(generatedId);
    }

    public void update(ReservationTime newReservationTime, Long id) {
        reservationTimeUpdatingDao.save(id, newReservationTime);
    }

    public void delete(Long id) {
        int delete = reservationTimeUpdatingDao.delete(id);

        if (delete == 0) {
            throw new ReservationNotFoundException(id);
        }
    }
}
