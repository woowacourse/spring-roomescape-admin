package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.common.Dao;
import roomescape.reservation.Reservation;
import roomescape.reservation.dto.AllReservationResponse;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservationTime.ReservationTime;

@Service
public class ReservationService {
    private final Dao<Reservation> reservationDao;
    private final Dao<ReservationTime> reservationTimeDao;

    public ReservationService(Dao<Reservation> reservationDao, Dao<ReservationTime> reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationResponse add(ReservationRequest reservationRequest) {
        Reservation savedReservation = reservationDao.add(reservationRequest.createReservation());
        ReservationTime findReservationTime = reservationTimeDao.getById(reservationRequest.timeId());
        return ReservationResponse.of(savedReservation, findReservationTime);
    }

    public AllReservationResponse getAll() {
        return AllReservationResponse.from(reservationDao.getAll());
    }

    public void deleteById(Long id) {
        reservationDao.deleteById(id);
    }
}
