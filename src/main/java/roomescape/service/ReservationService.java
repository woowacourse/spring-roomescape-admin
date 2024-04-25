package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.request.ReservationRequest;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationDAO;
import roomescape.repository.ReservationTimeDAO;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationService(ReservationDAO reservationDAO, ReservationTimeDAO reservationTimeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public List<Reservation> findAllReservations() {
        return reservationDAO.getAllReservations();
    }

    public Reservation addReservation(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimeDAO.findReservationById(request.timeId());
        return reservationDAO.addReservation(new Reservation(request.name(), request.date(), reservationTime));
    }

    public void deleteReservation(long id) {
        reservationDAO.deleteReservation(id);
    }
}
