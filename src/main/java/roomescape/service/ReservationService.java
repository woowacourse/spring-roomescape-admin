package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.dto.ReservationCreateRequestDto;
import roomescape.dto.ReservationResponseDto;

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
                .map(ReservationResponseDto::of)
                .toList();
    }

    public ReservationResponseDto add(ReservationCreateRequestDto requestDto) {
        ReservationCreateRequestDto validatedRequestDto = getValidatedRequestDto(requestDto);
        long id = reservationDao.add(validatedRequestDto);
        Reservation result = reservationDao.findById(id);
        return ReservationResponseDto.of(result);
    }

    public void delete(Long id) {
        reservationDao.delete(id);
    }

    private ReservationCreateRequestDto getValidatedRequestDto(ReservationCreateRequestDto requestDto) {
        ReservationTime reservationTime = reservationTimeDao.findById(requestDto.getTimeId());
        Reservation reservation = requestDto.toDomain(reservationTime);
        return ReservationCreateRequestDto.of(reservation, reservationTime);
    }
}
