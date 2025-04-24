package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.entity.ReservationTimeEntity;

import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationTimeService {
    private static final LocalTime OPERATING_START = LocalTime.of(10, 0);
    private static final LocalTime OPERATING_END = LocalTime.of(22, 0);
    private final ReservationTimeDao timeDao;

    public ReservationTimeService(ReservationTimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public ReservationTimeResponseDto create(ReservationTimeRequestDto requestDto) {
        ReservationTimeEntity entity = requestDto.toEntity();
        validateOperatingTime(entity);
        ReservationTimeEntity saved = timeDao.save(entity);
        return ReservationTimeResponseDto.from(saved);
    }

    private void validateOperatingTime(ReservationTimeEntity entity) {
        LocalTime startAt = entity.startAt();
        if (startAt.isBefore(OPERATING_START) || startAt.isAfter(OPERATING_END)) {
            throw new IllegalArgumentException("운영 시간 이외의 날짜는 예약할 수 없습니다.");
        }
    }

    public List<ReservationTimeResponseDto> getAllTimes() {
        return timeDao.findAll().stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    public void delete(final Long id) {
        final int deleted = timeDao.deleteById(id);
        if (deleted == 0) {
            throw new IllegalArgumentException("존재하지 않는 id 입니다.");
        }
    }
}
