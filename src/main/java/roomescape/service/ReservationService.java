package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Component;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationCreationRequest;
import roomescape.service.dto.ReservationCreationDto;

@Component
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> findAllReservations() {
        return reservationDao.findAll();
    }

    public Reservation createReservation(ReservationCreationRequest request) {
        return reservationDao.add(ReservationCreationDto.from(request));
    }

    public void cancelReservation(Long reservationId) {
        reservationDao.delete(reservationId);
    }

    public boolean checkReservationExist(Long reservationId) {
        return reservationDao.isExist(reservationId);
    }
}
