package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime.ReservationTimes;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.dto.response.ReservationCreateResponse;
import roomescape.dto.response.ReservationTimeResponse;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final ReservationTimes reservationTimes;

    public TimeController(final ReservationTimes reservationTimes) {
        this.reservationTimes = reservationTimes;
    }

    @GetMapping
    public List<ReservationTimeResponse> findAll() {
        return reservationTimes.findAll().stream()
                .map(reservationTime -> new ReservationTimeResponse(
                        reservationTime.getId(),
                        reservationTime.getStartAt()
                ))
                .toList();
    }

    @PostMapping
    public ReservationCreateResponse create(@RequestBody ReservationTimeCreateRequest reservationTimeCreateRequest) {
        return new ReservationCreateResponse(reservationTimes.create(reservationTimeCreateRequest),
                reservationTimeCreateRequest.startAt());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservationTimes.delete(id);
        return ResponseEntity.noContent().build();
    }
}
