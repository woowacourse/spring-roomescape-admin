package roomescape.service.reservation;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.service.dto.SaveReservationRequest;
import roomescape.repository.ReservationRepository;

@Service
public class ReservationCreateService {

    private final ReservationRepository reservationRepository;

    public ReservationCreateService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation createReservation(SaveReservationRequest request) {
        Reservation reservation = SaveReservationRequest.toEntity(request);
        return reservationRepository.save(reservation);
    }
}
