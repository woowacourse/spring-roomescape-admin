package roomescape.domain;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationRequest;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        return reservationRepository.create(reservationRequest.toDomain());
    }

    public void deleteReservation(Long id) {
        Reservation foundReservation = reservationRepository.findById(id)
                        .orElseThrow(() -> new IllegalStateException("해당 예약을 찾을 수 없습니다."));
        reservationRepository.deleteById(foundReservation.id());
    }
}
