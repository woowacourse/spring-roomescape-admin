package roomescape.reservation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.request.ReservationCreateRequest;
import roomescape.reservation.dto.response.ReservationCreateResponse;
import roomescape.reservation.dto.response.ReservationGetResponse;
import roomescape.reservation.repository.ReservationIdCache;
import roomescape.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationIdCache cache;

    public ReservationService(final ReservationRepository reservationRepository, final ReservationIdCache cache) {
        this.reservationRepository = reservationRepository;
        this.cache = cache;
    }

    public List<ReservationGetResponse> getReservations() {
        List<ReservationGetResponse> response = reservationRepository.getAll().stream()
                .map(reservation -> ReservationGetResponse.from(cache.get(reservation), reservation))
                .toList();
        cache.clear();
        return response;
    }

    public void delete(long id) {
        reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("요청한 id와 일치하는 예약 정보가 없습니다."));
        reservationRepository.deleteById(id);
    }

    public ResponseEntity<ReservationCreateResponse> create(final ReservationCreateRequest request) {
        Reservation newReservation = reservationRepository.put(request.toReservation());
        long newId = cache.get(newReservation);
        cache.clear();
        return ResponseEntity.ok(ReservationCreateResponse.from(newId, newReservation));

    }
}
