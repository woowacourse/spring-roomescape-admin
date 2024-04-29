package roomescape.domain;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.dto.ReservationRequest;
import roomescape.domain.dto.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        ReservationTime time = reservationTimeRepository.findById(reservationRequest.timeId())
                .orElseThrow(() -> new IllegalStateException("해당 예약 시간이 존재하지 않습니다."));
        Reservation reservation = reservationRequest.toDomain(time);

        Reservation createdReservation = reservationRepository.create(reservation);
        return ReservationResponse.from(createdReservation);
    }

    public void deleteReservation(Long id) {
        Reservation foundReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 예약을 찾을 수 없습니다."));
        reservationRepository.deleteById(foundReservation.id());
    }

}
