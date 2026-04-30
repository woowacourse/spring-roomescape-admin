package roomescape.service;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.ReservationDao;

@Service
public class ReservationCommandService {

    private final ReservationDao reservationDao;

    public ReservationCommandService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public ReservationResponse create(String name, LocalDate date, Long timeId) {
        Reservation savedReservation = reservationDao.save(Reservation.pending(name, date), timeId);
        return ReservationResponse.from(savedReservation);
    }

    public void delete(Long reservationId) {
        reservationDao.delete(reservationId);
    }
}
