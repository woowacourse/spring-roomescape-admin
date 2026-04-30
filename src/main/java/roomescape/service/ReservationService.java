package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dao.ReservationDAO;
import roomescape.dao.ReservationTimeDAO;
import roomescape.dto.ReservationRequest;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationService(ReservationDAO reservationDAO, ReservationTimeDAO reservationTimeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public Reservation makeReservation(ReservationRequest reservationRequest) {
        Long reservationId = reservationDAO.createReservation(reservationRequest);
        ReservationTime reservationTime = reservationTimeDAO.findById(reservationRequest.timeId());
        Reservation reservation = reservationRequest.toEntity(reservationTime, reservationId);

        return reservation;
    }

    public List<Reservation> findAllReservation() {
        return reservationDAO.readReservations();
    }

    public void deleteReservationById(Long id) {
        reservationDAO.deleteById(id);
    }

    public ReservationTime makeReservationTime(ReservationTime reservationTime) {
        Long id = reservationTimeDAO.createReservationTime(reservationTime);
        return ReservationTime.toEntity(reservationTime, id);
    }

    public List<ReservationTime> findAllReservationTime() {
        return reservationTimeDAO.readReservationTimes();
    }

    public void deleteReservationTimeById(Long id) {
        reservationTimeDAO.deleteById(id);
    }
}
