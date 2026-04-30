package roomescape.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public ReservationResponse addReservation(ReservationRequest request) {
        Reservation reservation = new Reservation(request.name(), request.date(),
                new ReservationTime(request.timeId(), null));
        Reservation savedReservation = reservationDao.insert(reservation);
        return ReservationResponse.from(savedReservation);
    }

    public List<ReservationResponse> findAllReservations() {
        List<Reservation> reservations = reservationDao.select();
        return reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    public void delete(Long reservationId) {
        reservationDao.delete(reservationId);
    }
}
