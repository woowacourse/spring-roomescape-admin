package roomescape.console;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import roomescape.console.request.ReservationTimeRequest;
import roomescape.console.response.ReservationTimeResponse;
import roomescape.core.domain.ReservationTime;
import roomescape.core.repository.ReservationTimeRepository;

import java.util.List;

@Controller
public class ReservationTimeController implements ConsoleController {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ResponseEntity<List<ReservationTimeResponse>> findAll() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        List<ReservationTimeResponse> reservationTimeResponses = reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok(reservationTimeResponses);
    }

    public ResponseEntity<ReservationTimeResponse> save(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeRequest.toEntity();

        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);

        return ResponseEntity.ok(ReservationTimeResponse.from(savedReservationTime));
    }

    public ResponseEntity<Void> save(Long id) {
        reservationTimeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
