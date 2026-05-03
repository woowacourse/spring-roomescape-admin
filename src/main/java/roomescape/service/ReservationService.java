package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.request.ReservationRequest;
import roomescape.controller.dto.response.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.exception.ReservationTimeNotFoundException;
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

    public List<Reservation> getReservationList() {
        return repository.findAll();
    }

    public ReservationResponse addReservation(ReservationRequest request) {
        ReservationTime reservationTime = timeRepository.findById(request.timeId())
                .orElseThrow(() -> new ReservationTimeNotFoundException(request.timeId()));
        Reservation reservation = new Reservation(
                request.name(),
                request.date(),
                reservationTime
        );

        Reservation saved = repository.save(reservation);

        return ReservationResponse.from(saved);
    }

    public void deleteReservation(Long id) {
        repository.delete(id);
    }
}
