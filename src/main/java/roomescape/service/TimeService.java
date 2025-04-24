package roomescape.service;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.request.TimeRequest;
import roomescape.dto.response.TimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.repository.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository repository;

    public TimeService(TimeRepository repository) {
        this.repository = repository;
    }

    public List<TimeResponse> getAllTimes() {
        List<ReservationTime> reservationTimes = repository.findAll();
        return TimeResponse.toDtos(reservationTimes);
    }

    public TimeResponse registerNewTime(TimeRequest request) {
        validateRequestTime(request.startAt());
        ReservationTime reservationTime = request.toDomain();
        Long id = repository.save(reservationTime);

        return TimeResponse.toDto(ReservationTime.assignId(id, reservationTime));
    }

    private void validateRequestTime(String time) {
        try {
            LocalTime.parse(time);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("유효하지 않은 시간입니다: " + time);
        }
    }

    public void deleteTime(Long id) {
        repository.deleteById(id);
    }

    public ReservationTime getTimeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제하려는 id가 존재하지 않습니다, id: " + id));
    }
}
