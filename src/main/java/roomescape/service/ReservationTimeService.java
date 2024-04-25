package roomescape.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import roomescape.core.exception.DeleteReservationTimeException;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationTimeRequest;
import roomescape.dto.CreateReservationTimeResponse;
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
        int createdId = reservationTimeDao.create(request);
        ReservationTime reservationTime = new ReservationTime(createdId, request.startAt());
        return reservationTimeMapper.toCreateReservationTimeResponse(reservationTime);
    }

    public void deleteTime(int timeId) {
        reservationDao.findAnyByTimeId(timeId)
                .ifPresentOrElse(
                        reservation -> {
                            throw new DeleteReservationTimeException(HttpStatus.BAD_REQUEST, "해당 예약시간에 예약이 등록되어 있습니다.");
                        },
                        () -> reservationTimeDao.delete(timeId)
                );
    }
}
