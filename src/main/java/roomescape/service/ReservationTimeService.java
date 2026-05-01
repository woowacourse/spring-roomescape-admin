package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.service.dto.ReservationTimeCreateCommand;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTime> findAll() {
        return reservationTimeDao.findAll();
    }

    public ReservationTime create(ReservationTimeCreateCommand command) {
        Long generatedId = reservationTimeDao.save(command.startAt());

        return new ReservationTime(generatedId, command.startAt());
    }

    public void delete(Long id) {
        reservationTimeDao.deleteById(id);
    }
}
