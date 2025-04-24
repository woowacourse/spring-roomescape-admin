package roomescape.reservation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationDao reservationDAO;

    public ReservationService(
            @Autowired final ReservationDao reservationDAO
    ) {
        this.reservationDAO = reservationDAO;
    }

    public ReservationResponse createReservation(final ReservationRequest reservationRequest) {
        final Reservation notSavedReservation = new Reservation(
                null,
                reservationRequest.name(),
                reservationRequest.date(),
                null
        );
        final Long savedReservationId = reservationDAO.saveReservation(notSavedReservation,
                reservationRequest.timeId());
        final Reservation savedReservation = reservationDAO.findReservationById(savedReservationId);
        return ReservationResponse.createResponse(savedReservation);
    }

    public List<ReservationResponse> findAllReservation() {
        return reservationDAO.findAllReservation().stream()
                .map(ReservationResponse::createResponse)
                .toList();
    }

    public void deleteReservationById(final Long id) {
        this.reservationDAO.deleteReservationById(id);
    }
}
