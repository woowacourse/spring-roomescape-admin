package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;

@Service
@Transactional
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponseDto> readReservation() {
        return reservationDao.findAll().stream()
                .map(ReservationResponseDto::toDto)
                .toList();
    }

    public ReservationResponseDto postReservation(ReservationRequestDto requestDto) {
        Reservation newReservation = reservationDao.save(requestDto.toEntity(), requestDto.timeId());
        return ReservationResponseDto.toDto(newReservation);
    }

    public void deleteReservation(long id) {
        reservationDao.deleteById(id);
    }
}
