package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.dto.ReservationTimeCreateRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.model.exception.ReservationTimeNotFoundException;
import roomescape.repository.ReservationTimeDao;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponse createReservationTime(final ReservationTimeCreateRequest request) {
        ReservationTime reservationTime = reservationTimeDao.insert(
                new ReservationTime(null, request.startAt()));
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }

    public List<ReservationTimeResponse> findAllReservationTimes() {
        return reservationTimeDao.findAll()
                .stream()
                .map(reservationTime -> new ReservationTimeResponse(
                        reservationTime.getId(), reservationTime.getStartAt()
                ))
                .toList();
    }

    public void deleteReservationTime(final Long id) {
        int updatedRow = reservationTimeDao.deleteById(id);

        if (updatedRow == 0) {
            throw new ReservationTimeNotFoundException("존재하지 않는 예약 시간 번호입니다.");
        }
    }
}
