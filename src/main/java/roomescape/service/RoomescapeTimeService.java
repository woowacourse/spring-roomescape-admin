package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.dto.TimeRequestDto;
import roomescape.dto.TimeResponseDto;
import roomescape.entity.ReservationTimeEntity;

import java.util.List;

@Service
public class RoomescapeTimeService {
    private final TimeDao timeDao;

    public RoomescapeTimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public TimeResponseDto create(TimeRequestDto requestDto) {
        ReservationTimeEntity saved = timeDao.save(requestDto.toEntity());
        return TimeResponseDto.from(saved);
    }

    public List<TimeResponseDto> getAllTimes() {
        return timeDao.findAll().stream()
                .map(TimeResponseDto::from)
                .toList();
    }

    public void delete(final Long id) {
        final int deleted = timeDao.deleteById(id);
        if (deleted == 0) {
            throw new IllegalArgumentException("존재하지 않는 id 입니다.");
        }
    }
}
