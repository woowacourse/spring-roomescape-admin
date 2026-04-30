package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeCreateReqDto;
import roomescape.dto.ReservationTimeResDto;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao timeDao;

    public ReservationTimeService(ReservationTimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public ReservationTimeResDto createTime(ReservationTimeCreateReqDto dto) {
        ReservationTime time = ReservationTime.create(dto.getStartAt());
        ReservationTime savedTime = timeDao.save(time);
        return ReservationTimeResDto.from(savedTime.getId(), savedTime.getStartAt());
    }

    public List<ReservationTimeResDto> getTimes() {
        List<ReservationTime> reservationTimes = timeDao.findAll();
        return reservationTimes.stream()
                .map(r -> ReservationTimeResDto.from(r.getId(), r.getStartAt()))
                .toList();
    }

    public void deleteTime(Long id) {
        timeDao.deleteById(id);
    }
}
