package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.web.dto.ReservationRequest;
import roomescape.web.dto.ReservationResponse;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public List<ReservationResponse> findAllReservation() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse saveReservation(ReservationRequest request) {
        Time time = findTimeById(request.timeId());
        Reservation reservation = request.toReservation(time);
        reservationDao.save(reservation);
        return ReservationResponse.from(reservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = findReservationById(id);
        reservationDao.delete(reservation);
    }

    private Reservation findReservationById(Long id) {
        return reservationDao.findById(id)
                .orElseThrow();
    }

    private Time findTimeById(Long id) {
        return timeDao.findById(id)
                .orElseThrow();
    }
}
