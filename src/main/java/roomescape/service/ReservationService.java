package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeService reservationTimeService;

    public ReservationService(ReservationDao reservationDao, ReservationTimeService reservationTimeService) {
        this.reservationDao = reservationDao;
        this.reservationTimeService = reservationTimeService;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public Reservation add(ReservationRequest request) {
        ReservationTime time = reservationTimeService.findById(request.timeId());
        Reservation reservation = Reservation.ofNew(request.name(), request.date(), time);
        return reservationDao.save(reservation);
    }

    public void delete(Long id) {
        reservationDao.deleteById(id);
    }
}
