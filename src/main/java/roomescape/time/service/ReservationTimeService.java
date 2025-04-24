package roomescape.time.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import roomescape.time.dao.ReservationTimeDao;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.ReservationTimeRequest;
import roomescape.time.dto.ReservationTimeResponse;
import roomescape.time.utils.ReservationTimeMapper;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;
    private final ReservationTimeMapper reservationTimeMapper;

    @Autowired
    public ReservationTimeService(ReservationTimeDao reservationTimeDao, ReservationTimeMapper reservationTimeMapper) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationTimeMapper = reservationTimeMapper;
    }

    public ReservationTimeResponse addTime(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeDao.insert(reservationTimeMapper.toTime(reservationTimeRequest));
        return reservationTimeMapper.toTimeResponse(reservationTime);
    }

    public List<ReservationTimeResponse> findAllTimes() {
        return reservationTimeDao.findAll()
                .stream()
                .map(reservationTimeMapper::toTimeResponse)
                .toList();
    }

    public void deleteTimeById(long id) {
        reservationTimeDao.delete(id);
    }
}
