package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;
import roomescape.exception.ReservationTimeInUseException;

@Service
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;
    private final ReservationDao reservationDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao, ReservationDao reservationDao) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationDao = reservationDao;
    }

    public List<ReservationTimeResponseDto> findAll() {
        return reservationTimeDao.findAll().stream()
                .map(ReservationTimeResponseDto::from)
                .toList();
    }

    public ReservationTimeResponseDto create(ReservationTimeRequestDto requestDto) {
        ReservationTime saved = reservationTimeDao.save(requestDto.toEntity());
        return ReservationTimeResponseDto.from(saved);
    }

    public boolean delete(Long id) {
        if (reservationDao.existsByTimeId(id)) {
            throw new ReservationTimeInUseException(id);
        }
        return reservationTimeDao.deleteById(id) > 0;
    }
}