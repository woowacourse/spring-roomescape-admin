package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public ReservationResponseDto saveReservation(ReservationRequestDto reservationResponseDto){
        Reservation reservation = reservationResponseDto.convertToReservation();
        Long id = reservationDao.saveReservation(reservation);
        Reservation findReservation = reservationDao.findReservationById(id);
        return ReservationResponseDto.from(findReservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationDao.findAll();
    }

    public void cancelReservation(Long id) {
        reservationDao.deleteById(id);
    }
}
