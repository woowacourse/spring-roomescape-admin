package roomescape.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;
import roomescape.domain.strategy.ReservationDateStrategy;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final ReservationDateStrategy strategy;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository reservationTimeRepository, ReservationDateStrategy strategy) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.strategy = strategy;
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        return convertToReservationResponses(reservations);
    }

    private List<ReservationResponse> convertToReservationResponses(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    public ReservationResponse create(ReservationRequest request) {
        Optional<ReservationTime> findReservationTime = reservationTimeRepository.findById(request.timeId());
        ReservationTime reservationTime = findReservationTime.orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 예약 시간 입니다."));
        Reservation reservation = reservationRepository.save(request.from(reservationTime, strategy));
        return ReservationResponse.from(reservation);
    }

    public void deleteById(Long id) {
        Optional<Reservation> findReservation = reservationRepository.findById(id);
        if (findReservation.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 예약 입니다.");
        }
        reservationRepository.deleteById(id);
    }
}
