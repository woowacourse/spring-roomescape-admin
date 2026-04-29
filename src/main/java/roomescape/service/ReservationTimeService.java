package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.dto.ReservationTimeRequestDto;
import roomescape.domain.entity.ReservationTime;

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
        reservationTimeDao.delete(id);
    }
}
