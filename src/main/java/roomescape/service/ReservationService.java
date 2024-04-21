package roomescape.service;

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

    public Reservation createReservation(ReservationCreationRequest request) {
        return reservationDao.add(ReservationCreationDto.from(request));
    }
}
