package roomescape.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@RequestMapping("/times")
@RestController
public class TimesController {

    ReservationTimeRepository reservationTimeRepository;

    public TimesController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @GetMapping
    ResponseEntity<List<ReservationTimeResponse>> getTimes() {
        List<ReservationTimeResponse> reservationTimes = reservationTimeRepository.findAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();

        return ResponseEntity.ok()
                .body(reservationTimes);
    }

    @PostMapping
    ResponseEntity<ReservationTimeResponse> addTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = ReservationTimeRequest.toReservationTime(reservationTimeRequest);
        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);
        ReservationTimeResponse reservationTimeResponse = ReservationTimeResponse.from(savedReservationTime);

        return ResponseEntity.ok()
                .body(reservationTimeResponse);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTime(@PathVariable("id") Long id) {
        reservationTimeRepository.deleteById(id);

        return ResponseEntity.ok()
                .build();
    }
}
