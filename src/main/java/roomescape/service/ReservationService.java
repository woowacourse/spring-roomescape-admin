package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.reservation.Reservation;
import roomescape.dto.ReservationResponseDto;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> getAllReservations() {
        return reservationDao.findAllReservation();
    }

    public ReservationResponseDto reserve(Reservation reservation) {
        Long savedId = reservationDao.addReservation(reservation);
        Reservation savedReservation = reservationDao.findById(savedId);
        return new ReservationResponseDto(savedReservation);
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteReservation(id);
    }
}
