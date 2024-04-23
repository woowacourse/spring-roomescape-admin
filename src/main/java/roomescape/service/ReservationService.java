package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.reservation.Reservation;
import roomescape.dto.ReservationRequestDto;
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

    public ReservationResponseDto reserve(ReservationRequestDto reservationRequestDto) {
        Long insertReservationId = reservationDao.addReservation(reservationRequestDto);
        Reservation insertReservation = reservationDao.findById(insertReservationId);
        return insertReservation.toResponseDto();
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteReservation(id);
    }
}
