package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.web.dto.ReservationFindAllResponse;
import roomescape.web.dto.ReservationSaveRequest;
import roomescape.web.dto.ReservationSaveResponse;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
    }

    public List<ReservationFindAllResponse> findAllReservation() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(ReservationFindAllResponse::from)
                .toList();
    }

    public ReservationSaveResponse saveReservation(ReservationSaveRequest request) {
        Time time = findTimeById(request.timeId());
        Reservation reservation = request.toReservation(time);
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

    private Time findTimeById(Long id) {
        return timeDao.findById(id)
                .orElseThrow();
    }
}
