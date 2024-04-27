package roomescape.console.controller;

import org.springframework.stereotype.Controller;
import roomescape.console.controller.request.ReservationRequest;
import roomescape.console.controller.response.ReservationResponse;
import roomescape.core.domain.Reservation;
import roomescape.core.domain.ReservationTime;
import roomescape.core.repository.ReservationRepository;
import roomescape.core.repository.ReservationTimeRepository;

import java.util.List;

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationController(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse save(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeRepository.findById(reservationRequest.timeId())
                .orElseThrow(() -> new IllegalArgumentException("예약할 수 없는 시간입니다. timeId: " + reservationRequest.timeId()));
        Reservation reservation = reservationRequest.toEntity(reservationTime);

        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationResponse.from(savedReservation);
    }

    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }
}
