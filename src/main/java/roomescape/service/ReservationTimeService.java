package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.service.mapper.ReservationMapper;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;
    private final ReservationDao reservationDao;
    private final ReservationMapper reservationMapper;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao, ReservationDao reservationDao, ReservationMapper reservationMapper) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationDao = reservationDao;
        this.reservationMapper = reservationMapper;
    }

    public List<ReservationTimeResponse> findAllReservationTimes() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();

        return reservationTimes.stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    public ReservationTimeResponse createReservationTime(ReservationTimeRequest request) {
        ReservationTime newReservationTime = new ReservationTime(
                null,
                request.startAt()
        );

        ReservationTime savedReservationTime = reservationTimeDao.insertReservationTime(newReservationTime);

        return reservationMapper.toResponse(savedReservationTime);
    }

    public void deleteReservationTime(Long id) {
        boolean isReserved = reservationDao.existsByTimeId(id);

        if (isReserved) {
            throw new IllegalArgumentException("이미 예약된 시간은 삭제할 수 없습니다.");
        }

        reservationTimeDao.deleteById(id);
    }
}
