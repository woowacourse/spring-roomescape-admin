package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationServiceImpl(final ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @Override
    public List<ReservationResponse> findAllReservations() {
        return reservationDAO.findAllReservation()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @Override
    public ReservationResponse createReservation(final ReservationRequest reservationRequest) {
        final Reservation reservation = reservationRequest.toEntity();
        final Long id = reservationDAO.insertReservation(reservation);
        reservation.setId(id);
        return ReservationResponse.from(reservation);
    }

    @Override
    public int deleteReservationById(final Long id) {
        return reservationDAO.deleteReservationById(id);
    }

    @Override
    public boolean existsById(final Long id) {
        return reservationDAO.existsById(id);
    }
}
