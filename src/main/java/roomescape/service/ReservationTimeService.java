package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimes;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;
    private final ReservationTimes reservationTimes;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao,
        ReservationTimes reservationTimes) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationTimes = reservationTimes;
    }

    public List<ReservationTimeResponseDto> getAllReservationTimes() {
        return reservationTimes.getReservationTimes().stream()
            .map(ReservationTimeResponseDto::from)
            .toList();
    }

    public ReservationTimeResponseDto saveReservationTime(
        ReservationTimeRequestDto reservationTimeRequestDto) {
        ReservationTime reservationTime = reservationTimeRequestDto.toReservationTime();
        reservationTimeDao.saveReservationTime(reservationTime);
        reservationTimes.addReservationTime(reservationTime);
        return ReservationTimeResponseDto.from(reservationTime);
    }

    public void deleteReservationTime(Long id) {
        reservationTimeDao.deleteReservationTime(id);
        reservationTimes.deleteById(id);
    }
}
