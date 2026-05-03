package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public Reservation create(ReservationRequest request) {
        return reservationDAO.insert(request.name(), request.date(), request.timeId());
    }

    public List<ReservationResponse> findAll() {
        return reservationDAO.findAll();
    }

    public void delete(Long id) {
        reservationDAO.delete(id);
    }

    public boolean existsByTimeId(Long timeId) {
        return reservationDAO.existsByTimeId(timeId);
    }
}
