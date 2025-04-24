package roomescape.reservation;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import roomescape.common.repository.CommonRepository;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.request.ReservationCreateRequest;
import roomescape.reservation.dto.response.ReservationResponse;

@Service
public class ReservationService {

    private final CommonRepository<Reservation> reservationRepository;

    public ReservationService(final CommonRepository<Reservation> reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationResponse> getReservations() {
        return reservationRepository.getAll().stream()
                .map(reservation -> ReservationResponse.from(reservationRepository.getCachedId(reservation),
                        reservation))
                .toList();
    }

    public void delete(long id) {
        reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("요청한 id와 일치하는 예약 정보가 없습니다."));
        reservationRepository.deleteById(id);
    }

    public ReservationResponse create(final ReservationCreateRequest request) {
        Reservation newReservation = reservationRepository.put(request.toReservation());
        long newId = reservationRepository.getCachedId(newReservation);
        return ReservationResponse.from(newId, newReservation);
    }
}
