package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.repository.H2ReservationDao;

import java.util.List;

@Service
public class ReservationService {

    private final H2ReservationDao reservationDao;
    private final ReservationTimeService reservationTimeService;

    @Autowired
    public ReservationService(H2ReservationDao reservationDao, ReservationTimeService reservationTimeService) {
        this.reservationDao = reservationDao;
        this.reservationTimeService = reservationTimeService;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public Reservation save(ReservationRequest reservationRequest) {
        ReservationTime time = reservationTimeService.findById(reservationRequest.timeId());
        return reservationDao.save(reservationRequest.toReservation(time));
    }

    public void deleteById(Long id) {
        reservationDao.deleteById(id);
    }
}
