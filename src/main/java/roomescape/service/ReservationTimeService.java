package roomescape.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeResponse;
import roomescape.dto.TimeSaveRequest;
import roomescape.mapper.TimeMapper;
import roomescape.repository.TimeDao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReservationTimeService {

    private final TimeMapper timeMapper = new TimeMapper();
    private final TimeDao timeDao;

    public ReservationTimeService(TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public List<TimeResponse> findAllTimes() {
        List<ReservationTime> reservationTimes = timeDao.findAll();
        return reservationTimes.stream()
                .map(timeMapper::mapToResponse)
                .toList();
    }

    public ReservationTime findTimeById(Long id) {
        Optional<ReservationTime> optionalReservationTime = timeDao.findById(id);
        if (optionalReservationTime.isEmpty()) {
            throw new NoSuchElementException("[ERROR] 예약 시간을 찾을 수 없습니다");
        }
        return optionalReservationTime.get();
    }

    public TimeResponse saveTime(TimeSaveRequest request) {
        ReservationTime reservationTime = timeMapper.mapToTime(request);
        Long saveId = timeDao.save(reservationTime);

        return timeMapper.mapToResponse(saveId, reservationTime);
    }

    public void deleteTimeById(Long id) {
        try {
            timeDao.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("[ERROR] 해당 시간에 예약이 존재합니다");
        }
    }
}
