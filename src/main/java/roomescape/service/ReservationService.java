package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationService {
    public static final int ONE_ROW = 1;
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> readReservations() {
        return reservationRepository.readReservations()
                .stream()
                .map(ReservationResponse::of)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toEntity();
        Long id = reservationRepository.createReservation(reservation);
        return ReservationResponse.of(reservationRepository.readReservationById(id));
    }

    public boolean deleteReservation(Long id) {
        return reservationRepository.deleteReservation(id) == ONE_ROW;
    }
}
