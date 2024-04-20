package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    public List<Reservation> getAllReservations() {
        return reservationDao.findAll();
    }

    public Reservation insertReservation(ReservationRequestDto reservationRequestDto) {
        Long id = reservationDao.insert(reservationRequestDto.name(), reservationRequestDto.date(), reservationRequestDto.timeId());
        return getReservation(id);
    }

    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }

    public Reservation getReservation(Long id) {
        return reservationDao.findById(id);
    }
}
