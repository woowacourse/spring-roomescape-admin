package roomescape.core.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.core.dto.ReservationRequest;
import roomescape.core.dto.ReservationResponse;
import roomescape.core.model.Reservation;
import roomescape.core.repository.ReservationRepository;

@Service
public class ReservationService {
    public static final int ONE_ROW = 1;
    private final ReservationRepository reservationRepository;

    @Autowired
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
