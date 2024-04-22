package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> readReservations() {
        return reservationDao.readAll();
    }

    public int createReservation(ReservationCreateRequest request) {
        return reservationDao.create(request.toReservation());
    }

    public void delete(int id) {
        reservationDao.delete(id);
    }
}
