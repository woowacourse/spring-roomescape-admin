package roomescape.service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;
import roomescape.service.dto.SaveReservationTimeDto;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository repository;

    public ReservationTimeService(ReservationTimeRepository repository) {
        this.repository = repository;
    }

    public ReservationTime save(SaveReservationTimeDto dto) {
        try {
            return repository.save(new ReservationTime(null, LocalTime.parse(dto.startAt())));
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("올바르지 않은 시간 입력입니다.");
        }
    }

    public List<ReservationTime> findAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
