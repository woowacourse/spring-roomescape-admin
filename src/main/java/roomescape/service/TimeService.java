package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.TimeRequest;
import roomescape.dto.response.TimeResponse;
import roomescape.mapper.ReservationTimeMapper;
import roomescape.repository.TimeRepository;

@Service
public class TimeService {

    private final TimeRepository repository;

    public TimeService(TimeRepository repository) {
        this.repository = repository;
    }

    public List<TimeResponse> getAllTimes() {
        List<ReservationTime> reservationTimes = repository.findAll();
        return ReservationTimeMapper.toDtos(reservationTimes);
    }

    public TimeResponse registerNewTime(TimeRequest request) {
        ReservationTime newReservationTime = ReservationTimeMapper.toDomain(request);
        Long id = repository.save(newReservationTime);

        ReservationTime saved = ReservationTime.assignId(id, newReservationTime);

        return ReservationTimeMapper.toDto(saved);
    }

    public void deleteTime(Long id) {
        repository.deleteById(id);
    }

    public ReservationTime getTimeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제하려는 id가 존재하지 않습니다, id: " + id));
    }
}
