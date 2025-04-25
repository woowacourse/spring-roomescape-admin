package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.dao.TimeDao;
import roomescape.domain_entity.Id;
import roomescape.domain_entity.ReservationTime;
import roomescape.dto.TimeRequestDto;

@Component
public class TimeService {

    @Autowired
    private TimeDao timeDao;

    public ReservationTime createTime(TimeRequestDto timeRequest) {
        ReservationTime reservationTime = timeRequest.toTime();
        long id = timeDao.create(reservationTime);
        reservationTime.setId(new Id(id));
        return reservationTime;
    }

    public List<ReservationTime> findAllTimes() {
        return timeDao.findAll();
    }

    public void deleteTime(long id) {
        timeDao.delteById(new Id(id));
    }
}
