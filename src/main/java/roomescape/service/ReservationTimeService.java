package roomescape.service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository repository;

    public ReservationTimeService(ReservationTimeRepository repository) {
        this.repository = repository;
    }

    public List<ReservationTime> getAll() {
        return repository.getAll();
    }

    public ReservationTime add(ReservationTimeRequest request) {
        if (request.startAt() == null || request.startAt().isBlank()) {
            throw new IllegalArgumentException("시작 시간은 필수 입력값입니다.");
        }

        LocalTime startAt;
        try {
            startAt = LocalTime.parse(request.startAt());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바르지 않은 시간 형식입니다. (HH:mm) startAt: " + request.startAt());
        }

        ReservationTime reservationTime = new ReservationTime(null, startAt);
        return repository.save(reservationTime);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
