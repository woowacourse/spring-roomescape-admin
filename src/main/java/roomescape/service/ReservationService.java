package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.repository.ReservationRepository;
import roomescape.repository.repository.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
        ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponse> getAll() {
        return reservationRepository.getAll().stream()
            .map(ReservationResponse::from)
            .toList();
    }

    public ReservationResponse create(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimeRepository.findById(request.timeId())
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 예약 시간입니다."));
        Reservation reservation = request.toEntity();
        Reservation saved = reservationRepository.save(reservation.withReservationTime(reservationTime));
        return ReservationResponse.from(saved);
    }

    public void remove(Long id) {
        reservationRepository.remove(id);
    }
}
