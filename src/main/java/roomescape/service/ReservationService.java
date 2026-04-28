package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.request.ReservationRequest;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public List<Reservation> getReservationList() {
        return repository.findAll();
    }

    public Reservation addReservation(ReservationRequest request) {
        Reservation reservation = new Reservation(
                request.name(),
                request.date(),
                request.time()
        );

        return repository.save(reservation);
    }

    public void deleteReservation(Long id) {
        repository.delete(id);
    }
}
