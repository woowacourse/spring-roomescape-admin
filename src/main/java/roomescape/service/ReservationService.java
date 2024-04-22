package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.dto.ReservationCreateRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeResponseDto;

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
                .map(this::convertResponseDto)
                .toList();
    }

    public ReservationResponseDto add(ReservationCreateRequestDto requestDto) {
        ReservationTime reservationTime = reservationTimeDao.findById(requestDto.getTimeId());
        ReservationTimeResponseDto timeResponseDto = ReservationTimeResponseDto.from(reservationTime);
        Reservation reservation = requestDto.toDomain(reservationTime);
        ReservationCreateRequestDto validatedRequestDto
                = ReservationCreateRequestDto.of(reservation, reservationTime);
        long id = reservationDao.add(validatedRequestDto);
        Reservation result = reservationDao.findById(id);
        return ReservationResponseDto.of(result, timeResponseDto);
    }

    public void delete(Long id) {
        reservationDao.delete(id);
    }

    private ReservationResponseDto convertResponseDto(Reservation reservation) {
        ReservationTime reservationTime = reservation.getReservationTime();
        ReservationTimeResponseDto timeResponseDto = ReservationTimeResponseDto.from(reservationTime);
        return ReservationResponseDto.of(reservation, timeResponseDto);
    }

}
