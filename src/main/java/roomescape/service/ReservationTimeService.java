package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.dto.ReservationTimeSaveDto;
import roomescape.entity.ReservationTime;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    @Autowired
    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponseDto> findTimes() {
        List<ReservationTime> times = reservationTimeDao.findAll();
        return times.stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    public ReservationTimeResponseDto addTime(ReservationTimeRequestDto reservationTimeDto) {
        long id = reservationTimeDao.save(ReservationTimeSaveDto.from(reservationTimeDto));
        ReservationTime reservationTime = reservationTimeDao.findById(id);
        return ReservationTimeResponseDto.from(reservationTime);
    }

    public void deleteTime(long id) {
        reservationTimeDao.delete(id);
    }
}
