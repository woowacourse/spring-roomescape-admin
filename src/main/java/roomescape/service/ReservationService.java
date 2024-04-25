package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDAO reservationDAO;
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationService(final ReservationDAO reservationDAO, final ReservationTimeDAO reservationTimeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public Reservation create(final ReservationRequest reservationRequest) {
        final Reservation reservation = new Reservation(
                reservationRequest.name(),
                reservationRequest.date(),
                reservationTimeDAO.findById(reservationRequest.timeId())
        );

        return reservationDAO.insert(reservation);
    }

    public List<Reservation> findAll() {
        return null;
    }

    public void delete() {

    }
}
