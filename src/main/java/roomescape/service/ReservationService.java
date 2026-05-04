package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.dao.ReservationDAO;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateRequest;
import roomescape.dto.ReservationCreateResponse;
import roomescape.dto.TimeCreateResponse;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationService(ReservationDAO reservationDAO, ReservationTimeDAO reservationTimeDAO) {
        this.reservationDAO = reservationDAO;
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public ReservationCreateResponse createReservation(ReservationCreateRequest request) {
        ReservationTime reservationTime = reservationTimeDAO.findTimeById(request.timeId());
        TimeCreateResponse timeCreateResponse = new TimeCreateResponse(request.timeId(), reservationTime.getStartAt());

        Reservation reservation = new Reservation(request.name(), request.date(), reservationTime);
        Long id = reservationDAO.insertWithKeyHolder(reservation, request.timeId());
        return new ReservationCreateResponse(id, reservation.getName(), reservation.getDate(), timeCreateResponse);
    }

    public List<ReservationCreateResponse> readAllReservations() {
        return reservationDAO.findAllReservations();
    }

    public void deleteReservation(Long id) {
        reservationDAO.delete(id);
    }
}
