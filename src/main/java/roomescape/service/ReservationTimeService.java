package roomescape.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponseDto> findAll() {
        return reservationTimeDao.findAll().stream()
                .map(ReservationTime::toDto)
                .toList();
    }

    public ReservationTime save(ReservationTimeRequestDto reservationTimeRequestDto) {
        return reservationTimeDao.save(reservationTimeRequestDto);
    }

    public void delete(long id) throws DataIntegrityViolationException {
        reservationTimeDao.delete(id);
    }
}
