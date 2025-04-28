package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeDao;
import roomescape.service.dto.ReservationTimeRegisterDto;
import roomescape.service.dto.ReservationTimeResponseDto;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public Long saveReservationTime(final ReservationTimeRegisterDto reservationTimeRegisterDto) {
        ReservationTime reservationTime = reservationTimeRegisterDto.toReservationTime();
        return reservationTimeDao.save(reservationTime);
    }

    public ReservationTime findReservationTimeById(final Long id) {
        return reservationTimeDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id 와 일치하는 예약 시각이 존재하지 않습니다."));
    }

    public List<ReservationTimeResponseDto> findAllReservationTimes() {
        return reservationTimeDao.findAll().stream()
                .map(ReservationTimeResponseDto::new)
                .toList();
    }

    public void deleteReservationTimeById(final Long id) {
        findReservationTimeById(id);
        reservationTimeDao.deleteById(id);
    }

}
