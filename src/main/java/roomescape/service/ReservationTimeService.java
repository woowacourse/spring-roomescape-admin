package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.dto.ReservationTimeCreateRequestDto;
import roomescape.dto.ReservationTimeResponseDto;

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
        reservationTimeDao.delete(id);
    }

    private ReservationTimeCreateRequestDto getValidatedRequestDto(ReservationTimeCreateRequestDto requestDto) {
        ReservationTime reservationTime = requestDto.toDomain();
        return ReservationTimeCreateRequestDto.from(reservationTime);
    }
}
