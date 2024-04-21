package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime insertReservationTime(ReservationTimeRequestDto reservationTimeRequestDto) {
        Long id = reservationTimeDao.insert(reservationTimeRequestDto.startAt());
        return new ReservationTime(id, reservationTimeRequestDto.startAt());
    }

    public List<ReservationTime> getAllReservationTimes() {
        return reservationTimeDao.findAll();
    }

    public void deleteReservationTime(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
