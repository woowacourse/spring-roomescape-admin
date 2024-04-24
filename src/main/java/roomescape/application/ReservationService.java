package roomescape.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimeRepository;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservations;
    private final ReservationTimeRepository reservationTimes;

    public ReservationService(ReservationRepository reservations,
                              ReservationTimeRepository reservationTimes) {
        this.reservations = reservations;
        this.reservationTimes = reservationTimes;
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = this.reservations.findAll();
        return convertToReservationResponses(reservations);
    }

    private List<ReservationResponse> convertToReservationResponses(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }

    public ReservationResponse create(ReservationRequest request) {
        ReservationTime reservationTime = reservationTimes.findById(request.timeId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 시간 입니다."));
        Reservation reservation = reservations.save(request.toReservation(reservationTime));
        return ReservationResponse.from(reservation);
    }

    public void deleteById(long id) {
        Optional<Reservation> findReservation = reservations.findById(id);
        if (findReservation.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 예약 입니다.");
        }
        reservations.deleteById(id);
    }
}
