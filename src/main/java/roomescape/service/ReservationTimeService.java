package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeResponse;
import roomescape.dto.ReservationTimeSaveRequest;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeDao;
import roomescape.repository.dto.ReservationTimeSaveDto;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponse> getTimes() {
        return reservationTimeDao.findAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTimeResponse saveTime(final ReservationTimeSaveRequest reservationTimeSaveRequest) {
        final ReservationTimeSaveDto reservationTimeSaveDto = ReservationTimeSaveDto.from(reservationTimeSaveRequest);
        final ReservationTime savedReservationTime = reservationTimeDao.save(reservationTimeSaveDto);
        return ReservationTimeResponse.from(savedReservationTime);
    }

    public void deleteTime(final Long id) {
        boolean isDeleted = reservationTimeDao.deleteById(id);
        if (isDeleted) {
            return;
        }
        throw new IllegalArgumentException("존재하지 않는 예약 시간입니다.");
    }
}
