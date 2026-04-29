package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<Reservation> read() {
        return reservationDAO.read();
    }

    public ReservationResponse create(ReservationRequest request) {
        Reservation reservation = new Reservation(
                request.name(),
                request.date(),
                request.time()
        );

        return reservationDAO.save(reservation);
    }

    public int delete(Long id) {
        return reservationDAO.delete(id);
    }
}
