package roomescape.reservation;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping("/times")
    public ResponseEntity<?> postTimes(@Valid @RequestBody ReservationTimeRequest request) {
        ReservationTime reservationTime = reservationTimeRepository.save(request);
        return ResponseEntity.ok().body(ReservationTimeResponse.from(reservationTime));
    }

}
