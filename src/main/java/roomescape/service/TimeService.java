package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.dto.TimeRequestDto;
import roomescape.dto.TimeResponseDto;
import roomescape.model.Time;

@Service
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<TimeResponseDto> getAllTimes() {
        List<Time> timeInfos = timeDao.findAll();
        return timeInfos.stream()
                .map(TimeResponseDto::from)
                .toList();
    }

    public TimeResponseDto saveTime(TimeRequestDto timeRequestDto) {
        Time time = timeRequestDto.convertToTime();
        Long id = timeDao.saveTime(time);
        return new TimeResponseDto(id, time.getTime());
    }
}
