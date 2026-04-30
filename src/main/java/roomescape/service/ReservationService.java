package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation create(ReservationRequestDto requestDto) {
        return reservationDao.create(requestDto);
    }

    public List<Reservation> readAll() {
        return reservationDao.readAll();
    }

    public void delete(Long id) {
        reservationDao.delete(id);
    }
}
