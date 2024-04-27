package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.TimeManagementDao;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequest;
import roomescape.dto.TimeRequest;

import java.util.List;

@Service
public class TimeManagementService {
    private final TimeManagementDao timeManagementDao;

    public TimeManagementService(TimeManagementDao timeManagementDao) {
        this.timeManagementDao = timeManagementDao;
    }

    public List<Time> read() {
        return timeManagementDao.findAll();
    }

    public Time create(TimeRequest timeRequest) {
        return timeManagementDao.insert(timeRequest);
    }

    public void delete(long id) {
        timeManagementDao.delete(id);
    }

    public Time findById(ReservationRequest reservationRequest) {
        return timeManagementDao.findById(reservationRequest.timeId());
    }
}
