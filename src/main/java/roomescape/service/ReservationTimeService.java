package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationTimeRequest;
import roomescape.dto.CreateReservationTimeResponse;
import roomescape.exception.ReservationExistsForTimeException;
import roomescape.mapper.ReservationTimeMapper;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;
    private final ReservationDao reservationDao;
    private final ReservationTimeMapper reservationTimeMapper;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao, ReservationDao reservationDao, ReservationTimeMapper reservationTimeMapper) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationDao = reservationDao;
        this.reservationTimeMapper = reservationTimeMapper;
    }

    public List<ReservationTime> findAllTimes() {
        return reservationTimeDao.findAll();
    }

    public CreateReservationTimeResponse createTime(CreateReservationTimeRequest request) {
        ReservationTime requestedReservationTime = reservationTimeMapper.toReservationTime(request);
        ReservationTime reservationTime = reservationTimeDao.create(requestedReservationTime);
        return reservationTimeMapper.toCreateReservationTimeResponse(reservationTime);
    }

    public void deleteTime(long timeId) {
        reservationDao.findAnyByTimeId(timeId)
                .ifPresentOrElse(
                        reservation -> {
                            throw new ReservationExistsForTimeException(timeId);
                        },
                        () -> reservationTimeDao.delete(timeId)
                );
    }
}
