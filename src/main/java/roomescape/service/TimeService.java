package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.domain.Time;

import java.util.List;

@Service
public class TimeService {

    @Autowired
    TimeDao timeDao;

    public Time selectTime(Time request) {
        Long id = timeDao.save(request);
        return Time.toEntity(id, request);
    }

    public List<Time> findAll() {
        return timeDao.readAll();
    }

    public void deleteTime(Long id) {
        timeDao.delete(id);
    }
}
