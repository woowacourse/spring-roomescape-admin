package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationResponse createReservation(final ReservationCreateRequest request) {
        final Long id = reservationRepository.save(request);
        final Reservation reservation = request.toReservation(id);
        return new ReservationResponse(reservation);
    }

    public List<Reservation> readAllReservations() {
        return reservationRepository.findAll();
    }

    public void cancelReservation(final Long id) {
        reservationRepository.deleteById(id);
    }
}
