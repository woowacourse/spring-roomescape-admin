package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.web.dto.ReservationFindAllResponse;
import roomescape.web.dto.ReservationSaveRequest;
import roomescape.web.dto.ReservationSaveResponse;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationFindAllResponse> findAllReservation() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationFindAllResponse::from)
                .toList();
    }

    public ReservationSaveResponse saveReservation(ReservationSaveRequest request) {
        Reservation reservation = request.toReservation();
        reservationDao.save(reservation);
        return ReservationSaveResponse.from(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = findReservationById(id);
        reservationDao.delete(reservation);
    }

    private Reservation findReservationById(Long id) {
        return reservationDao.findById(id)
                .orElseThrow();
    }
}
