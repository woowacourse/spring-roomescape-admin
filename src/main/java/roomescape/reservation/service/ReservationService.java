package roomescape.reservation.service;

import org.springframework.stereotype.Service;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.exception.EntityNotFoundException;
import roomescape.reservation.repository.ReservationDao;
import roomescape.reservation.repository.ReservationTimeDao;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponseDto> findAll() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
            .map(ReservationResponseDto::toDto)
            .toList();
    }

    public ReservationResponseDto add(ReservationRequestDto requestDto) {
        ReservationTime reservationTime = reservationTimeDao.findById(requestDto.timeId())
            .orElseThrow(() -> new EntityNotFoundException("선택한 예약 시간이 존재하지 않습니다."));
        Reservation reservation = new Reservation(requestDto.name(), requestDto.date(), reservationTime);

        Reservation saved = reservationDao.save(reservation);
        return ReservationResponseDto.toDto(saved);
    }

    public void deleteById(Long id) {
        reservationDao.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("삭제할 예약정보가 없습니다."));

        reservationDao.deleteById(id);
    }
}
