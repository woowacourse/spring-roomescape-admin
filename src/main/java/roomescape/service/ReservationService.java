package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.dto.ReservationRequest;
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
    public ReservationResponse create(ReservationRequest request) {
        ReservationTime time = timeRepository.findById(request.getTimeId());
        Long id = repository.create(request);
        Reservation reservation = new Reservation(id, request.getName(), request.getDate(), time);
        return ReservationResponse.from(reservation);
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
