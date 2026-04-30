package roomescape;

import java.util.List;
import org.springframework.stereotype.Service;

@Service

public class RoomescapeService {

    private final ReservationDAO reservationDAO;

    public RoomescapeService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }


    public List<Reservation> readReservation() {
        return reservationDAO.findAllReservation();
    }

    public ReservationTime getReservationTime(Long timeId) {
        return reservationDAO.findReservationTimeById(timeId);
    }

    public Long addReservation(Reservation newReservation) {
        return reservationDAO.add(newReservation);
    }


    public void deleteReservation(Long id) {
        reservationDAO.delete(id);
    }

    public ReservationTime addReservationTime(ReservationTime reservationTime) {
        return reservationDAO.addReservationTime(reservationTime);

    }

    public List<ReservationTime> findAllReservationTime() {
        return reservationDAO.findAllReservationTime();
    }


    public void deleteReservationTime(Long id) {
        reservationDAO.deleteReservationTime(id);
    }
}
