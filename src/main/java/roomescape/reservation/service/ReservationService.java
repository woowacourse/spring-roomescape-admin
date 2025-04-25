package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.dao.ReservationDAO;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.time.dao.TimeDAO;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.TimeResponse;

@Service
public class ReservationService {
    private final ReservationDAO reservationDAO;
    private final TimeDAO timeDAO;

    public ReservationService(final ReservationDAO reservationDAO, final TimeDAO timeDAO) {
        this.reservationDAO = reservationDAO;
        this.timeDAO = timeDAO;
    }

    public ReservationResponse createReservation(final ReservationRequest reservationRequest) {
        ReservationTime reservationTime = timeDAO.findReservationTimeById(reservationRequest.timeId());
        Reservation notSavedreservation = new Reservation(
                null, reservationRequest.name(), reservationRequest.date(), reservationTime
        );
        Reservation savedReservation = reservationDAO.insertReservation(notSavedreservation);
        return new ReservationResponse(
                savedReservation.getId(),
                savedReservation.getName(),
                savedReservation.getDate(),
                new TimeResponse(savedReservation.getTime().getId(), savedReservation.getTime().getStartAt())
        );
    }

    public void removeReservation(final long id) {
        reservationDAO.removeReservation(id);
    }

    public List<ReservationResponse>  findAllReservations() {
        List<Reservation> reservations = reservationDAO.findAllReservations();

        return reservations.stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        new TimeResponse(reservation.getTime().getId(), reservation.getTime().getStartAt())
                )).toList();
    }
}
