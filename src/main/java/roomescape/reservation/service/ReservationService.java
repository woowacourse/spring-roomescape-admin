package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse create(CreateReservationRequest createReservationRequest) {
        Long id = reservationRepository.save(
                new Reservation(null, createReservationRequest.name(), createReservationRequest.date(),
                        createReservationRequest.time()));
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("예약 생성에 실패했습니다."));
        return ReservationResponse.from(reservation);
    }

    public ReservationResponse delete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 예약입니다."));
        reservationRepository.delete(id);
        return ReservationResponse.from(reservation);
    }
}
