package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.dto.reservationtime.ReservationTimeCreateRequestDto;
import roomescape.dto.reservationtime.ReservationTimeResponseDto;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponseDto> findAll() {
        List<ReservationTime> allReservationTimes = reservationTimeDao.findAll();
        return allReservationTimes.stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    public ReservationTimeResponseDto add(ReservationTimeCreateRequestDto requestDto) {
        ReservationTimeCreateRequestDto validatedRequestDto = getValidatedRequestDto(requestDto);
        long id = reservationTimeDao.add(validatedRequestDto);
        ReservationTime result = reservationTimeDao.findById(id);
        return ReservationTimeResponseDto.from(result);
    }

    public void delete(Long id) {
        validateNull(id);
        validateExist(id);
        reservationTimeDao.delete(id);
    }

    private ReservationTimeCreateRequestDto getValidatedRequestDto(ReservationTimeCreateRequestDto requestDto) {
        ReservationTime reservationTime = requestDto.toDomain();
        return ReservationTimeCreateRequestDto.from(reservationTime);
    }

    private void validateNull(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("예약 시간 아이디는 비어있을 수 없습니다.");
        }
    }

    private void validateExist(Long id) {
        if (!reservationTimeDao.exist(id)) {
            throw new IllegalArgumentException("해당 아이디를 가진 예약 시간이 존재하지 않습니다.");
        }
    }
}
