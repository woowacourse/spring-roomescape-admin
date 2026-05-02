package roomescape.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequestDto;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public Reservation create(ReservationRequestDto requestDto) {
        try {
            ReservationTime reservationTime = reservationTimeDao.read(requestDto.timeId());
            return reservationDao.create(requestDto, reservationTime);
        } catch (NoSuchElementException exception) {
            throw new IllegalArgumentException("[ERROR] 해당 id의 예약 시간이 존재하지 않습니다.");
        }
    }

    public List<Reservation> readAll() {
        return reservationDao.readAll();
    }

    public void delete(Long id) {
        try {
            reservationDao.read(id);
            reservationDao.delete(id);
        } catch (NoSuchElementException exception) {
            throw new IllegalArgumentException("[ERROR] 해당 id의 예약이 존재하지 않습니다.");
        }
    }
}
