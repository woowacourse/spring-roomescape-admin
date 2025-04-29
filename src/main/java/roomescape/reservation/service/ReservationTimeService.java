package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.dao.ReservationTimeDAO;
import roomescape.reservation.dto.ReservationTimeReqDTO;
import roomescape.reservation.model.ReservationTime;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDAO timeDAO;

    public ReservationTimeService(ReservationTimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    public ReservationTime create(ReservationTimeReqDTO timeDto) {
        ReservationTime reservationTimeInfo = timeDto.toEntity();
        return timeDAO.insert(reservationTimeInfo);
    }

    public List<ReservationTime> getAll() {
        return timeDAO.selectAll();
    }

    public void deleteBy(Long timeId) {
        timeDAO.deleteBy(timeId);
    }
}
