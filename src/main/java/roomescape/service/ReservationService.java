package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.model.Reservation;
import roomescape.repository.ReservationDAO;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<Reservation> findAllReservations() {
        return reservationDAO.selectAllReservations();
    }

    public Reservation addReservation(Reservation reservation) {
        return reservationDAO.insertReservation(reservation);
    }

    public void removeReservation(long id) {
        reservationDAO.deleteReservation(id);
    }
}
