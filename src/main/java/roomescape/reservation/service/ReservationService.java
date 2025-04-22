package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.controller.request.ReservationRequest;
import roomescape.reservation.controller.response.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(final ReservationRepository repository) {
        this.repository = repository;
    }

    public List<ReservationResponse> get() {
        return repository.findAll();
    }

    public ReservationResponse create(final ReservationRequest request) {
        final long id = repository.add(request);
        return repository.findById(id);
    }

    public void deleteById(final Long id) {
        repository.deleteById(id);
    }
}
