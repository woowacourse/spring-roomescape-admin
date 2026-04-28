package roomescape.reservation;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public ReservationResponse save(ReservationRequest request) {
        Reservation reservation = reservationDao.save(request.name(), request.date(), request.timeId());
        return ReservationResponse.from(reservation);
    }

    public void delete(Long id) {
        reservationDao.delete(id);
    }
}
