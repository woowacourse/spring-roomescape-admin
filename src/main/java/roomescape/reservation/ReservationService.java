package roomescape.reservation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import roomescape.reservation.dto.request.ReservationCreateRequest;
import roomescape.reservation.dto.response.ReservationCreateResponse;
import roomescape.reservation.dto.response.ReservationGetResponse;
import roomescape.reservation.entity.ReservationEntity;
import roomescape.reservation.repository.ReservationRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationGetResponse> getReservations() {
        return ReservationGetResponse.from(reservationRepository.getAll());
    }

    public void delete(long id) {
        reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("요청한 id와 일치하는 예약 정보가 없습니다."));
        reservationRepository.deleteById(id);
    }

    public ResponseEntity<ReservationCreateResponse> create(final ReservationCreateRequest request) {
        ReservationEntity reservationEntity = reservationRepository.put(request.toReservation());
        return ResponseEntity.ok(ReservationCreateResponse.of(reservationEntity));

    }
}
