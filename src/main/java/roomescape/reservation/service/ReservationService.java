package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.domain.ReservationTime;
import roomescape.time.repository.ReservationTimeRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse create(CreateReservationRequest createReservationRequest) {
        ReservationTime reservationTime = reservationTimeRepository.findById(createReservationRequest.timeId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 예약 시간입니다."));

        Long id = reservationRepository.save(
                Reservation.create(createReservationRequest.name(), createReservationRequest.date(),
                        reservationTime));
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
