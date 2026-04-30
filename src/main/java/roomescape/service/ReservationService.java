package roomescape.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public ReservationResponse addReservation(ReservationRequest request) {
        Reservation reservation = reservationDao.insert(request);
        return ReservationResponse.from(reservation);
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
