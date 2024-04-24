package roomescape.time.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservation.dto.ResponseCode;
import roomescape.time.dao.ReservationTimeDao;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.ReservationTimeRequestDto;
import roomescape.time.dto.ReservationTimeResponseDto;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(final ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public List<ReservationTimeResponseDto> findAll() {
        final List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        return reservationTimes.stream()
                               .map(ReservationTimeResponseDto::from)
                               .toList();
    }

    @Transactional
    public ReservationTimeResponseDto save(final ReservationTimeRequestDto requestDto) {
        final long id = reservationTimeDao.save(requestDto);
        return new ReservationTimeResponseDto(id, requestDto.startAt());
    }

    @Transactional
    public ResponseCode deleteById(final long id) {
        try {
            if (reservationTimeDao.deleteById(id) > 0) {
                return ResponseCode.SUCCESS_DELETE;
            }
            return ResponseCode.NOT_FOUND;
        } catch (final DataAccessException dataAccessException) {
            return ResponseCode.FAILED_DELETE;
        }
    }
}
