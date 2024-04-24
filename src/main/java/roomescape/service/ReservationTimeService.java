package roomescape.service;

import java.time.LocalTime;
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
        return repository.save(
            new ReservationTime(null, LocalTime.parse(dto.startAt()))
        );
    }

    public List<ReservationTime> findAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
