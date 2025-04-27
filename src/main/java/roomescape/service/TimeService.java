package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import roomescape.dao.JdbcTimeDao;
import roomescape.domain_entity.Id;
import roomescape.domain_entity.ReservationTime;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.dto.ReservationTimeRequestDto;

@Component
public class TimeService {

    @Autowired
    private JdbcTimeDao timeDao;

    public ReservationTimeResponseDto createTime(ReservationTimeRequestDto timeRequest) {
        ReservationTime reservationTime = timeRequest.toTime();
        long id = timeDao.create(reservationTime);
        reservationTime.setId(new Id(id));
        return ReservationTimeResponseDto.from(reservationTime);
    }

    public List<ReservationTimeResponseDto> findAllTimes() {
        return timeDao.findAll().stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    public void deleteTime(long id) {
        timeDao.deleteById(new Id(id));
    }
}
