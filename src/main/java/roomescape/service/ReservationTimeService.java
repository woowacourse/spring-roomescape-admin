package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.dao.ReservationTimeDAO;
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeCreateResponse;

@Service
public class ReservationTimeService {
    private final ReservationTimeDAO reservationTimeDAO;

    public ReservationTimeService(ReservationTimeDAO reservationTimeDAO) {
        this.reservationTimeDAO = reservationTimeDAO;
    }

    public TimeCreateResponse createTime(String startAt) {
        ReservationTime reservationTime = new ReservationTime(startAt);
        Long id = reservationTimeDAO.insertWithKeyHolder(reservationTime);
        return new TimeCreateResponse(id, startAt);
    }

    public List<TimeCreateResponse> readAllTimes() {
        return reservationTimeDAO.findAllTimes();
    }

    public void deleteTime(Long id) {
        reservationTimeDAO.deleteTime(id);
    }
}
