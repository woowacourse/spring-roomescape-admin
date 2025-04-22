package roomescape.service;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.Reservation;
import roomescape.controller.dto.ReservationRequest;
import roomescape.repository.ReservationRepository;
import roomescape.service.dto.ReservationResponse;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<ReservationResponse> getReservations() {
        final List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationResponse::new)
                .toList();
    }

    public ReservationResponse saveReservation(final @Valid ReservationRequest request) {
        if (reservationRepository.existsByDateAndTime(request.date(), request.time())) {
            throw new IllegalArgumentException("해당 시간은 이미 예약되어있습니다.");
        }

        final Reservation newReservation = reservationRepository.save(request.name(), request.date(), request.time());
        return new ReservationResponse(newReservation);
    }

    public void deleteReservation(final Long id) {
        final Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
        reservationRepository.remove(reservation.getId());
    }
}
