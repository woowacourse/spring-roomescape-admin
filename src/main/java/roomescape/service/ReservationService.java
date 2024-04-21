package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.CreateReservationRequest;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public List<Reservation> findAll() {
        return repository.findAll();
    }

    public Reservation save(CreateReservationRequest request) {
        return repository.save(request);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
