package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.reservation.Name;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservationDate;
import roomescape.dto.ReservationRequestDto;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<Reservation> getAllReservations() {
        return reservationDao.findAllReservation();
    }

    public Reservation reserve(ReservationRequestDto reservationRequestDto) {
        Long savedId = reservationDao.addReservation(createReservation(reservationRequestDto));
        return reservationDao.findById(savedId);
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteReservation(id);
    }

    private Reservation createReservation(ReservationRequestDto reservationRequestDto) {
        return new Reservation(new Name(reservationRequestDto.name()), new ReservationDate(reservationRequestDto.date()),
                reservationTimeDao.findById(reservationRequestDto.timeId()));
    }
}
