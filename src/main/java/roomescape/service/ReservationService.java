package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeResponse;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationTime;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(reservation -> {
                    ReservationTimeResponse reservationTimeResponse = ReservationTimeResponse.from(reservation.getTime());
                    return ReservationResponse.of(reservation, reservationTimeResponse);
                }).toList();
    }

    public ReservationResponse insert(ReservationRequest request) {
        Long timeId = request.timeId();
        ReservationTime reservationTime = reservationTimeDao.findById(timeId);
        Long reservationId = reservationDao.save(request.toReservation(reservationTime));
        Reservation reservation = reservationDao.findById(reservationId);
        return ReservationResponse.of(
                reservation,
                ReservationTimeResponse.from(reservationTime)
        );
    }

    public void delete(Long id) {
        reservationDao.deleteById(id);
    }
}
