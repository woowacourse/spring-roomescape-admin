package roomescape.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import roomescape.dto.Time;
import roomescape.dto.TimeRequest;

@Repository
public class TimeRepository {
    private final TimeDao timeDao;

    public TimeRepository(final TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public Time saveTime(final TimeRequest timeRequest) {
        return timeDao.save(timeRequest);
    }

    public List<Time> getTimes() {
        return timeDao.getAll();
    }

    public void deleteTime(final long id) {
        timeDao.delete(id);
    }
}
