package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.Reservation;
import roomescape.ReservationTime;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationDto;
import roomescape.dto.ReservationRequest;

@Service
public class ReservationService {

    private ReservationDao reservationDao;
    private ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public Reservation findById(long id) {
        ReservationDto reservationDto = reservationDao.findById(id);
        ReservationTime reservationTime = reservationTimeDao.findById(reservationDto.timeId());
        return new Reservation(reservationDto.id(), reservationDto.name(), reservationDto.date(), reservationTime);
    }

    public int save(ReservationRequest reservationRequest) {
        return reservationDao.save(reservationRequest);
    }

    public void deleteById(long id) {
        reservationDao.deleteById(id);
    }
}
