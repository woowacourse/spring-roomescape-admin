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

    public List<Reservation> getReservations() {
        return reservationDAO.findAllReservations();
    }

    public long addReservations(Reservation reservation) {
        return reservationDAO.add(reservation);
    }

    public void deleteReservation(long id) {
        reservationDAO.delete(id);
    }
}
