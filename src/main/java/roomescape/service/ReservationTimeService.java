package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.dto.reservationtime.ReservationTimeCreateRequest;
import roomescape.dto.reservationtime.ReservationTimeResponse;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponse> findAll() {
        List<ReservationTime> allReservationTimes = reservationTimeDao.readAll();
        return allReservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse add(ReservationTimeCreateRequest request) {
        ReservationTime reservationTime = request.toDomain();
        ReservationTime result = reservationTimeDao.create(reservationTime);
        return ReservationTimeResponse.from(result);
    }

    public void delete(Long id) {
        validateNull(id);
        validateNotExist(id);
        reservationTimeDao.delete(id);
    }

    private void validateNull(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("예약 시간 아이디는 비어있을 수 없습니다.");
        }
    }

    private void validateNotExist(Long id) {
        if (!reservationTimeDao.exist(id)) {
            throw new IllegalArgumentException("해당 아이디를 가진 예약 시간이 존재하지 않습니다.");
        }
    }
}
