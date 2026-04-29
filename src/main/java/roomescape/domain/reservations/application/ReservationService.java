package roomescape.domain.reservations.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.reservations.entity.Reservation;
import roomescape.domain.reservations.entity.ReservationTime;
import roomescape.domain.reservations.infrastructure.ReservationRepository;
import roomescape.domain.reservations.infrastructure.ReservationTimeRepository;
import roomescape.domain.reservations.presentation.dto.ReservationRequest;
import roomescape.domain.reservations.presentation.dto.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            ReservationTimeRepository reservationTimeRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @Transactional
    public ReservationResponse saveReservation(ReservationRequest request) {
        validateSaveRequest(request);
        ReservationTime time = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(IllegalArgumentException::new);
        Reservation reservation = Reservation.of(
                null,
                request.name(),
                request.date(),
                time
        );
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationResponse.from(savedReservation);
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public void deleteReservation(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        reservationRepository.deleteById(id);
    }

    private void validateSaveRequest(ReservationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException();
        }
        if (request.name() == null || request.name().trim().isBlank()) {
            throw new IllegalArgumentException();
        }
        if (request.date() == null) {
            throw new IllegalArgumentException();
        }
        if (request.timeId() == null) {
            throw new IllegalArgumentException();
        }
    }
}
