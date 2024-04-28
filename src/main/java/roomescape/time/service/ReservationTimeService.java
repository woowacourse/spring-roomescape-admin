package roomescape.time.service;

import org.springframework.stereotype.Service;
import roomescape.time.dao.ReservationTimeDao;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.ReservationTimeRequestDto;
import roomescape.time.dto.ReservationTimeResponseDto;
import roomescape.time.dto.ReservationTimesResponseDto;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponseDto addReservationTime(final ReservationTimeRequestDto request) {
        ReservationTime reservationTime = reservationTimeDao.insert(new ReservationTime(request.startAt()));

        return new ReservationTimeResponseDto(reservationTime.getId(), reservationTime.getStartAt());
    }

    public ReservationTimesResponseDto findAllReservationTime() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        List<ReservationTimeResponseDto> response = reservationTimes.stream()
                .map(rt -> new ReservationTimeResponseDto(rt.getId(), rt.getStartAt()))
                .toList();

        return new ReservationTimesResponseDto(response);
    }

    public void deleteReservationTimeById(final Long id) {
        int deleteCount = reservationTimeDao.deleteById(id);

        if (deleteCount == 0) {
            throw new IllegalArgumentException(String.format("ID %d - ReservationTime 정보가 존재하지 않습니다.", id));
        }
    }
}
