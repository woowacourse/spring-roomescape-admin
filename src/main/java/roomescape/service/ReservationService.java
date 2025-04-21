package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.model.Reservation;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }
    public void saveReservation(ReservationRequestDto reservationResponseDto){
        Reservation reservation = reservationResponseDto.convertToReservation();
        reservationDao.saveReservation(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationDao.findAll();
    }
}
