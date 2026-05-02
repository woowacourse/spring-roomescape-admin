package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.TimeRequest;
import roomescape.controller.dto.TimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@RestController
public class ReservationTimeController {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> create(@RequestBody TimeRequest request) {
        ReservationTime reservationTime = ReservationTime.from(null, request.startAt());
        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);

        TimeResponse timeResponse = TimeResponse.from(savedReservationTime);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(timeResponse);
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> findAll() {

        List<TimeResponse> timeResponses = reservationTimeRepository.findAll()
                .stream()
                .map(TimeResponse::from)
                .toList();

        return ResponseEntity.ok(timeResponses);
    }


    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
