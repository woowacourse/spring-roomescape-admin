package roomescape.reservation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.request.ReservationCreateRequest;
import roomescape.reservation.dto.response.ReservationCreateResponse;
import roomescape.reservation.dto.response.ReservationGetResponse;
import roomescape.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationGetResponse> getReservations() {
        return reservationRepository.getAll().stream()
                .map(reservation -> ReservationGetResponse.from(reservationRepository.getCachedId(reservation),
                        reservation))
                .toList();
    }

    public void delete(long id) {
        reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("요청한 id와 일치하는 예약 정보가 없습니다."));
        reservationRepository.deleteById(id);
    }

    public ResponseEntity<ReservationCreateResponse> create(final ReservationCreateRequest request) {
        Reservation newReservation = reservationRepository.put(request.toReservation());
        long newId = reservationRepository.getCachedId(newReservation);
        reservationRepository.clearAllCachedIds();
        return ResponseEntity.ok(ReservationCreateResponse.from(newId, newReservation));
    }
}
