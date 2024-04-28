package roomescape.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;

@Service
public class ReservationTimeService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponseDto> findAll() {
        return reservationTimeDao.findAll().stream()
                .map(ReservationTime::toDto)
                .toList();
    }

    public ReservationTime save(ReservationTimeRequestDto reservationTimeRequestDto) {
        return reservationTimeDao.save(reservationTimeRequestDto);
    }

    public void delete(long id) {
        List<Reservation> reservations = reservationDao.findByTimeId(id);
        if(!reservations.isEmpty()) {
            throw new IllegalArgumentException("해당 예약 시간은 사용 중입니다.");
        }
        reservationTimeDao.delete(id);
    }
}
