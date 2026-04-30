package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationsResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository repository;
    private final ReservationTimeRepository timeRepository;

    public ReservationService(ReservationRepository repository, ReservationTimeRepository timeRepository) {
        this.repository = repository;
        this.timeRepository = timeRepository;
    }

    @Transactional
    public ReservationResponse create(Reservation reservation) {
        ReservationTime time = timeRepository.findById(reservation.getTime().getId());
        Long id = repository.create(reservation);
        Reservation newReservation = new Reservation(id, reservation.getName(), reservation.getDate(), time);
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
