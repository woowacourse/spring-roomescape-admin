package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.dao.ReservationDAO;
import roomescape.reservation.dao.ReservationTimeDAO;
import roomescape.reservation.dto.ReservationReqDTO;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.model.ReservationTime;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationService(ReservationDAO reservationDAO, ReservationTimeDAO reservationTimeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public Reservation create(ReservationReqDTO dto) {
        ReservationTime reservationTime = reservationTimeDAO.selectBy(dto.timeId());
        Reservation reservationInfo = dto.toEntityWith(reservationTime);
        return reservationDAO.insert(reservationInfo);
    }

    public Reservation getBy(Long reservationId) {
        return reservationDAO.selectBy(reservationId);
    }

    public List<Reservation> getAll() {
        return reservationDAO.selectAll();
    }

    public void deleteBy(Long reservationId) {
        reservationDAO.deleteBy(reservationId);
    }
}
