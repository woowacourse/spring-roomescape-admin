package roomescape.controller;

import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.db.ReservationTimeRepository;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;


@Controller
@RequestMapping("/times")
public class TimeController {
    @Autowired
    ReservationTimeRepository reservationTimeRepository;

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> postTimes(
            @RequestBody ReservationTimeRequest reservationTimeRequest) {
        Long id = reservationTimeRepository.createReservationTime(reservationTimeRequest);
        return ResponseEntity.status(201).body(ReservationTimeResponse.from(reservationTimeRepository.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getTimes() {
        return ResponseEntity.ok()
                .body(reservationTimeRepository.getReservationTimes()
                        .stream()
                        .map(ReservationTimeResponse::from)
                        .toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        reservationTimeRepository.deleteById(id);
        return ResponseEntity.status(204).header("Location", "/reservations/" + id).build();
    }
}
