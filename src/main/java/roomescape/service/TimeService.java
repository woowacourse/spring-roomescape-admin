package roomescape.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import roomescape.dto.TimeRequest;
import roomescape.dto.TimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.TimeDao;

@Service
public class TimeService {

    private final TimeDao timeDao;

    public TimeService(final TimeDao timeDao) {
        this.timeDao = timeDao;
    }

    public TimeResponse save(final TimeRequest timeRequest) {
        final ReservationTime created = ReservationTime.create(timeRequest.startAt());
        final ReservationTime saved = timeDao.save(created);
        return TimeResponse.from(saved);
    }

    public List<TimeResponse> findAll() {
        return timeDao.findAll()
                .stream()
                .map(TimeResponse::from)
                .toList();
    }

    public void remove(final long id) {
        //TODO findById 메서드 제거하기 reservation도
        final Optional<ReservationTime> findReservation = findById(id);
        if (findReservation.isEmpty()) {
            throw new IllegalArgumentException(String.format("timeId: %s는 존재하지 않는 timeId 입니다.", id));
        }
        timeDao.remove(id);
    }

    private Optional<ReservationTime> findById(final long id) {
        return timeDao.findById(id);
    }
}
