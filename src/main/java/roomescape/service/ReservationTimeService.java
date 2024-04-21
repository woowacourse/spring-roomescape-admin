package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.entity.ReservationTime;

@Service
public class ReservationTimeService {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    public List<ReservationTimeResponseDto> findTimes() {
        List<ReservationTime> times = reservationTimeDao.findAllReservationTimes();
        return times.stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    public ReservationTimeResponseDto addTime(ReservationTimeRequestDto reservationTimeDto) {
        long id = reservationTimeDao.save(reservationTimeDto);
        ReservationTime reservationTime = reservationTimeDao.findById(id);
        return ReservationTimeResponseDto.from(reservationTime);
    }

    public void deleteTime(long id) {
        reservationTimeDao.delete(id);
    }
}
