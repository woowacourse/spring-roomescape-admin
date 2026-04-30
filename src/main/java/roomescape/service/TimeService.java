package roomescape.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;
import roomescape.service.command.CreateTimeCommand;

import java.util.List;

@Service
@Transactional
public class TimeService {
    private final TimeDao timeDao;

    public TimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    @Transactional(readOnly = true)
    public List<Time> findAll() {
        return timeDao.findAll().toTimes();
    }

    public Time createTime(CreateTimeCommand command) {
        Time time = new Time(command.getTime());
        Long id = timeDao.insert(time);

        return timeDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 시간입니다."))
                .toTime();
    }

    public void deleteTime(Long id) {
        int deleted = timeDao.delete(id);

        if (deleted < 1) {
            throw new IllegalArgumentException("존재하지 않는 시간입니다. id: " + id);
        }
    }
}
