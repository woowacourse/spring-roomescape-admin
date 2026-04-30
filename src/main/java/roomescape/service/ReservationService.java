package roomescape.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationService(ReservationDAO reservationDAO, ReservationTimeDAO reservationTimeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public List<Reservation> read() {
        return reservationDAO.findAll();
    }

    public Reservation create(String name, LocalDate date, Long timeId) {
        ReservationTime time = reservationTimeDAO.findById(timeId);
        Reservation reservation = new Reservation(name, date, time);

        return reservationDAO.create(reservation);
    }

    public int delete(Long id) {
        return reservationDAO.delete(id);
    }
}
