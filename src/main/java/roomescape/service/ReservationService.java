package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.CreateReservationResponse;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> readReservations() {
        return reservationDao.findAll();
    }

    public CreateReservationResponse createReservation(CreateReservationRequest request) {
        int createdId = reservationDao.create(request);
        Reservation reservation = reservationDao.findById(createdId);
        return CreateReservationResponse.from(reservation);
    }

    public void delete(int id) {
        reservationDao.delete(id);
    }
}
