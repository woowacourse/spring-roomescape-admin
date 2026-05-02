package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeCreateReqDto;
import roomescape.dto.ReservationTimeResDto;
import roomescape.repository.ReservationTimeDao;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class ReservationTimeService {

    private final ReservationTimeDao timeDao;

    public ReservationTimeService(ReservationTimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @Transactional
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

    @Transactional
    public void deleteTime(Long id) {
        timeDao.deleteById(id);
    }
}
