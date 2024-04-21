package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.CreateReservationTimeRequest;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository repository;

    public ReservationTimeService(ReservationTimeRepository repository) {
        this.repository = repository;
    }

    public ReservationTime save(CreateReservationTimeRequest request) {
        return repository.save(request);
    }

    public List<ReservationTime> findAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
