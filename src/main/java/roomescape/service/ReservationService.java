package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationsResponse;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ReservationResponse create(Reservation reservation) {
        Long id = repository.create(reservation);
        Reservation newReservation = new Reservation(id, reservation.getName(), reservation.getDate(), reservation.getTime());
        return ReservationResponse.from(newReservation);
    }

    @Transactional
    public ReservationsResponse findAll() {
        List<Reservation> responses = repository.findAll();
        return ReservationsResponse.from(responses);
    }

    @Transactional
    public int delete(long id) {
        return repository.delete(id);
    }
}
