package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeDto;
import roomescape.entity.ReservationTime;

@Service
public class ReservationTimeService {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    public List<ReservationTimeDto> findTimes() {
        List<ReservationTime> times = reservationTimeDao.findAllReservationTimes();
        return times.stream()
                .map(ReservationTimeDto::from)
                .toList();
    }

    public ReservationTimeDto addTime(ReservationTimeDto reservationTimeDto) {
        long id = reservationTimeDao.save(reservationTimeDto);
        return new ReservationTimeDto(id, reservationTimeDto.getStartAt());
    }

    public void deleteTime(long id) {
        reservationTimeDao.delete(id);
    }
}
