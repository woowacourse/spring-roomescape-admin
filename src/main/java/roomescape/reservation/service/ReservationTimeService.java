package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationTimeRequestDto;
import roomescape.reservation.dto.ReservationTimeResponseDto;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.exception.EntityNotFoundException;
import roomescape.reservation.repository.ReservationTimeDao;

import java.util.List;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTimeResponseDto add(ReservationTimeRequestDto requestDto) {
        ReservationTime reservationTime = new ReservationTime(requestDto.startAt());
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);
        return ReservationTimeResponseDto.toDto(savedReservationTime);
    }

    public List<ReservationTimeResponseDto> findAll() {
        List<ReservationTime> times = reservationTimeDao.findAll();
        return times.stream()
            .map(ReservationTimeResponseDto::toDto)
            .toList();
    }

    public void deleteById(Long id) {
        reservationTimeDao.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("삭제할 예약시간이 없습니다."));

        reservationTimeDao.deleteById(id);
    }
}
