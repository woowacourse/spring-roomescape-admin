package roomescape.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.strategy.ReservationDateStrategy;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {
    private final ReservationRepository repository;
    private final ReservationDateStrategy strategy;

    public ReservationService(ReservationRepository repository, ReservationDateStrategy strategy) {
        this.repository = repository;
        this.strategy = strategy;
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = repository.findAll();
        return convertToReservationResponses(reservations);
    }

    private List<ReservationResponse> convertToReservationResponses(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    public ReservationResponse create(ReservationRequest request) {
        Reservation reservation = repository.save(request.from(strategy));
        return ReservationResponse.from(reservation);
    }

    public void deleteById(Long id) {
        Optional<Reservation> findReservation = repository.findById(id);
        if (findReservation.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 예약 입니다.");
        }
        repository.deleteById(id);
    }
}
