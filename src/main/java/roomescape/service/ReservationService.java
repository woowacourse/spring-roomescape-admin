package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequestDto;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    public List<Reservation> findAll() {
        return reservationDao.readAll();
    }

    public Reservation create(ReservationRequestDto request) {
        Long timeId = request.getTimeId();
        String startAt = reservationDao.findStartTimeByTimeId(timeId);
        ReservationTime reservationTime = new ReservationTime(timeId, startAt);
        Reservation reservation = new Reservation(request, reservationTime);
        Long id = reservationDao.save(reservation);
        return Reservation.toEntity(id, reservation);
    }

    public void delete(Long id) {
        reservationDao.delete(id);
    }
}
