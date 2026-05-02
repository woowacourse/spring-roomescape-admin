package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime create(ReservationTimeRequestDto requestDto) {
        return reservationTimeDao.create(requestDto);
    }

    public List<ReservationTime> readAll() {
        return reservationTimeDao.readAll();
    }

    public void delete(Long id) {
        if (reservationTimeDao.delete(id) == 0) {
            throw new IllegalArgumentException("[ERROR] 해당 id의 예약 시간이 존재하지 않습니다.");
        }
    }
}
