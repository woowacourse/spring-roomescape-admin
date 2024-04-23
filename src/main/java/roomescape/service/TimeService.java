package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.TimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeCreateRequest;

import java.util.List;

@Service
public class TimeService {
    private final TimeDao dao;

    public TimeService(TimeDao dao) {
        this.dao = dao;
    }

    public List<ReservationTime> readTimes() {
        return dao.readTimes();
    }

    public ReservationTime createTime(TimeCreateRequest dto) {
        return dto.createTime(
                dao.createTime(dto.startAt())
        );
    }

    public void deleteTime(long id) {
        dao.deleteTime(id);
    }
}
