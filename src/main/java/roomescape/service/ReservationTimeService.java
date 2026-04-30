package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationTimeRequest;
import roomescape.domain.ReservationTime;
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
        LocalTime startAt = LocalTime.parse(request.startAt());

        ReservationTime reservationTime = new ReservationTime(null, startAt);
        return repository.save(reservationTime);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
