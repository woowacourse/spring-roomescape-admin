package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.CreateReservationRequest;
import roomescape.controller.dto.CreateReservationResponse;
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

    public CreateReservationResponse createReservation(CreateReservationRequest request) {
        int createdId = reservationDao.create(request);
        Reservation reservation = reservationDao.read(createdId);
        return CreateReservationResponse.from(reservation);
    }

    public void delete(int id) {
        reservationDao.delete(id);
    }
}
