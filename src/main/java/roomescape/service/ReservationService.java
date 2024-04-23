package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> getAllReservations() {
        return reservationDao.findAll();
    }

    @Transactional
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
