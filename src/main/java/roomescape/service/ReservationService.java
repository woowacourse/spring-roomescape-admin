package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        Reservation reservation = Reservation.of(
            request.name(),
            request.date(),
            request.time()
        );
        Reservation saved = reservationRepository.save(reservation);
        return ReservationResponse.from(saved);
    }

    public List<ReservationResponse> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        List<ReservationResponse> responses = new ArrayList<>();
        for (Reservation reservation : reservations) {
            ReservationResponse response = ReservationResponse.from(reservation);
            responses.add(response);
        }
        return responses;
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
