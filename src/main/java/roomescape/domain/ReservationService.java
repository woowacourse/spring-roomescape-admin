package roomescape.domain;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.entity.Reservation;
import roomescape.web.dto.ReservationFindAllResponse;
import roomescape.web.dto.ReservationFindResponse;
import roomescape.web.dto.ReservationSaveRequest;

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

    public ReservationFindResponse saveReservation(ReservationSaveRequest request) {
        Reservation reservation = request.toEntity();
        reservationDao.save(reservation);
        return ReservationFindResponse.from(reservation);
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
