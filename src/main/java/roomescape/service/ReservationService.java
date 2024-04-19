package roomescape.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.dao.ReservationRepository;
import roomescape.domain.Reservation;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(@Qualifier("JdbcRepository") ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse scheduleReservation(ReservationRequest request) {
        Reservation reservation = request.toInstance();
        Reservation savedReservation = reservationRepository.addReservation(reservation);
        return ReservationResponse.from(savedReservation);
    }

    public void cancelReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new IllegalArgumentException("id에 해당하는 예약을 찾을 수 없습니다.");
        }
        reservationRepository.deleteById(id);
    }

    public void cancelAllReservations() {
        reservationRepository.deleteAll();
    }
}
