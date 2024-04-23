package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationAddRequest;
import roomescape.repository.ReservationDao;

@Service
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    public List<Reservation> findAllReservation() {
        return reservationDao.findAll();
    }

    public Reservation addReservation(ReservationAddRequest reservationAddRequest) {
        return reservationDao.insert(reservationAddRequest);
    }

    public boolean removeReservation(Long id) {
        return reservationDao.deleteById(id) > 0;
    }
}
