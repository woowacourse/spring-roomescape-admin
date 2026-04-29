package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

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

    public ReservationResponse create(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimeDAO.findById(request.timeId());
        Reservation reservation = new Reservation(
                request.name(),
                request.date(),
                reservationTime
        );

        return reservationDAO.create(reservation);
    }

    public int delete(Long id) {
        return reservationDAO.delete(id);
    }
}
