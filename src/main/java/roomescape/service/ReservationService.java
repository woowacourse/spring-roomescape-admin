package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> findAll(){
        return reservationDao.findAll();
    }

    public void delete(Long id){
        reservationDao.deleteById(id);
    }

    public Reservation save(ReservationRequest reservationRequest) {
        Long timeId = reservationRequest.timeId();
        ReservationTime time = reservationTimeDao.findById(timeId);
        Reservation reservation = new Reservation( reservationRequest.name(), reservationRequest.date(), time);
        return reservationDao.save(reservation);
    }
}
