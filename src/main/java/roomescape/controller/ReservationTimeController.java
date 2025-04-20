package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeCreateRequest;
import roomescape.dto.response.ReservationTimeResponse;

@Controller
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> getTimes() {
        List<ReservationTimeResponse> responses = reservationTimeDao.findAll()
                .stream()
                .map(ReservationTimeResponse::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationTimeResponse> getTimeById(@PathVariable long id) {
        ReservationTime reservationTime = reservationTimeDao.findById(id);
        ReservationTimeResponse response = ReservationTimeResponse.from(reservationTime);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> createTime(@RequestBody ReservationTimeCreateRequest request) {
        ReservationTime reservationTime = request.toReservationTime();

        ReservationTime saved = reservationTimeDao.save(reservationTime);

        ReservationTimeResponse response = ReservationTimeResponse.from(saved);
        URI uri = URI.create("/times/" + response.id());
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeById(@PathVariable long id) {
        reservationTimeDao.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
