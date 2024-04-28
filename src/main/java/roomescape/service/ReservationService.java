package roomescape.service;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequestDto;
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
        return reservationDao.findAll().stream()
                .map(Reservation::toDto)
                .toList();
    }

    public Reservation save(ReservationRequestDto reservationRequestDto) throws IncorrectResultSizeDataAccessException {
        ReservationTime reservationTime = reservationTimeDao.findById(reservationRequestDto.timeId());
        return reservationDao.save(new Reservation(null, reservationRequestDto.name(), reservationRequestDto.date(), reservationTime));
    }

    public void delete(Long id) throws DataIntegrityViolationException  {
        reservationDao.delete(id);
    }
}
