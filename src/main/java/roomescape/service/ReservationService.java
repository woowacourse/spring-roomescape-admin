package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.dto.reservation.ReservationCreateRequestDto;
import roomescape.dto.reservation.ReservationResponseDto;

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
                .map(ReservationResponseDto::from)
                .toList();
    }

    public ReservationResponseDto add(ReservationCreateRequestDto requestDto) {
        validateExistReservationTime(requestDto.getTimeId());
        ReservationCreateRequestDto validatedRequestDto = getValidatedRequestDto(requestDto);
        long id = reservationDao.add(validatedRequestDto);
        Reservation result = reservationDao.findById(id);
        return ReservationResponseDto.from(result);
    }

    public void delete(Long id) {
        validateNull(id);
        validateExistReservation(id);
        reservationDao.delete(id);
    }

    private ReservationCreateRequestDto getValidatedRequestDto(ReservationCreateRequestDto requestDto) {
        ReservationTime reservationTime = reservationTimeDao.findById(requestDto.getTimeId());
        Reservation reservation = requestDto.toDomain(reservationTime);
        return ReservationCreateRequestDto.of(reservation, reservationTime);
    }

    private void validateNull(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("예약 아이디는 비어있을 수 없습니다.");
        }
    }

    private void validateExistReservation(Long id) {
        if (!reservationDao.exist(id)) {
            throw new IllegalArgumentException("해당 아이디를 가진 예약이 존재하지 않습니다.");
        }
    }

    private void validateExistReservationTime(Long id) {
        if (!reservationTimeDao.exist(id)) {
            throw new IllegalArgumentException("예약 시간 아이디에 해당하는 예약 시간이 존재하지 않습니다.");
        }
    }
}
