package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.TimeManagementDao;
import roomescape.domain.Time;
import roomescape.dto.TimeRequest;

import java.util.List;
@Service
public class TimeManagementService {
    private final TimeManagementDao timeManagementDao;

    public TimeManagementService(TimeManagementDao timeManagementDao) {
        this.timeManagementDao = timeManagementDao;
    }

    public List<Time> findAll() {
        return timeManagementDao.findAll();
    }

    public Time create(TimeRequest timeRequest) {
        return timeManagementDao.insert(timeRequest);
    }

    public void delete(long id) {
        timeManagementDao.delete(id);
    }
}
