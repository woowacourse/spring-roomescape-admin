package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.request.ReservationTimeRequest;
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationTimeService {

    private final ReservationTimeRepository repository;

    public ReservationTimeService(final ReservationTimeRepository repository) {
        this.repository = repository;
    }

    public List<ReservationTimeResponse> get() {
        return repository.findAll();
    }

    public ReservationTimeResponse create(final ReservationTimeRequest request) {
        final long id = repository.add(request);
        return repository.findById(id);
    }

    public void deleteById(final Long id) {
        repository.deleteById(id);
    }
}
