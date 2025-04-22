package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;

@Service
public class ReservationService {

    private static final int DUPLICATED_RESERVATION = -1;

    private final ReservationDAO reservationDAO;

    public ReservationService(final ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<Reservation> findAll() {
        return reservationDAO.findAll();
    }

    public long addReservation(final Reservation reservation) {
        if (existsSameDateTime(reservation)) {
            return DUPLICATED_RESERVATION;
        }
        return reservationDAO.insert(reservation);
    }

    public boolean removeReservationById(final long id) {
        return reservationDAO.deleteById(id);
    }

    private boolean existsSameDateTime(final Reservation reservation) {
        return reservationDAO.existsByDateAndTimeId(reservation.getDate(), reservation.getTime().getId());
    }
}
