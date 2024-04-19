package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.model.Reservation;
import roomescape.repository.ReservationDao;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(final ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public long save(final ReservationRequest reservationRequest) {
        return reservationDao.save(reservationRequest);
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }
}
