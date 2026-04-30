package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeDAO timeDAO;

    public ReservationTimeService(ReservationTimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    public List<ReservationTime> read() {
        return timeDAO.read();
    }

    public ReservationTime create(LocalTime startAt) {
        ReservationTime time = new ReservationTime(startAt);
        return timeDAO.create(time);
    }

    public int delete(Long id) {
        return timeDAO.delete(id);
    }
}
