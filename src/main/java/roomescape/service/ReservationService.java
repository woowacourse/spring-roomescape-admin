package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public Reservation create(String name, String date, Long timeId) {
        return reservationDAO.insert(name, date, timeId);
    }

    public List<Reservation> findAll() {
        return reservationDAO.findAll();
    }

    public void delete(Long id) {
        reservationDAO.delete(id);
    }
}
