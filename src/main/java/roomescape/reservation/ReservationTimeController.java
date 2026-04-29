package roomescape.reservation;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/times")
    public ResponseEntity<?> getAllTimes() {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        return ResponseEntity.ok().body(reservationTimes);
    }

}
