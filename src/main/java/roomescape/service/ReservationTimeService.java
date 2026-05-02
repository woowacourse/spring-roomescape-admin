package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeCreateReqDto;
import roomescape.dto.ReservationTimeResDto;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationTimeDao;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class ReservationTimeService {

    private final ReservationTimeDao timeDao;
    private final ReservationDao reservationDao;

    public ReservationTimeService(ReservationTimeDao timeDao, ReservationDao reservationDao) {
        this.timeDao = timeDao;
        this.reservationDao = reservationDao;
    }

    @Transactional
    public ReservationTimeResDto createTime(ReservationTimeCreateReqDto dto) {
        ReservationTime time = ReservationTime.create(dto.getStartAt());
        ReservationTime savedTime = timeDao.save(time);
        return ReservationTimeResDto.from(savedTime);
    }

    public List<ReservationTimeResDto> getTimes() {
        List<ReservationTime> reservationTimes = timeDao.findAll();
        return reservationTimes.stream()
                .map(ReservationTimeResDto::from)
                .toList();
    }

    @Transactional
    public void deleteTime(Long id) {
        if (reservationDao.existsByTimeId(id)) {
            throw new IllegalStateException("예약이 있는 시간은 삭제할 수 없습니다.");
        }
        timeDao.deleteById(id);
    }
}
