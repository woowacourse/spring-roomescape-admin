package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.request.ReservationRequest;
import roomescape.repository.web.dao.ReservationDao;
import roomescape.repository.web.dao.ReservationTimeDao;
import roomescape.domain.reservation.Name;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDate;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> getAllReservations() {
        return reservationDao.findAll();
    }

    public Reservation reserve(ReservationRequest reservationRequest) {
        if (hasSameTimeReservation(reservationRequest)) {
            throw new IllegalArgumentException("이미 예약된 시간입니다.");
        }

        return reservationDao.add(createReservation(reservationRequest));
    }

    public void deleteReservation(Long id) {
        reservationDao.delete(id);
    }

    private Reservation createReservation(ReservationRequest reservationRequest) {
        return new Reservation(new Name(reservationRequest.name()), new ReservationDate(reservationRequest.date()),
                reservationTimeDao.findById(reservationRequest.timeId())
                        .orElseThrow(() -> new NoSuchElementException(reservationRequest.timeId() + "에 해당하는 시간이 없습니다."))
        );
    }

    private boolean hasSameTimeReservation(ReservationRequest reservationRequest) {
        return !reservationDao.findAllByDateTime(createReservation(reservationRequest)).isEmpty();
    }
}
