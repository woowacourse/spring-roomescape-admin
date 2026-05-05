package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.exception.ReservationTimeNotFoundException;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationService(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationResponseDto> findAll() {
        return reservationDao.findAll().stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    public ReservationResponseDto create(ReservationRequestDto requestDto) {
        ReservationTime time = reservationTimeDao.findById(requestDto.timeId())
                .orElseThrow(() -> new ReservationTimeNotFoundException(requestDto.timeId()));
        Reservation saved = reservationDao.save(requestDto.toEntity(time));
        return ReservationResponseDto.from(saved);
    }

    public boolean delete(Long id) {
        return reservationDao.deleteById(id) > 0;
    }
}
