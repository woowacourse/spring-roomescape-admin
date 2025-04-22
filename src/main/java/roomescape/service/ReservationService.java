package roomescape.service;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;

@Service
@Validated
public class ReservationService {

    private final ReservationRepository reservationDao;

    public ReservationService(ReservationRepository reservationDao) {
        this.reservationDao = reservationDao;
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }

    public long addReservation(@Valid Reservation newReservation) {
        return reservationDao.add(newReservation);
    }

    public List<Reservation> allReservations() {
        return reservationDao.findAll();
    }
}
