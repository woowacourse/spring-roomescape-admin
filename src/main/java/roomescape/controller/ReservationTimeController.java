package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.ReservationTimeDto;
import roomescape.service.ReservationTimeService;

@Controller
public class ReservationTimeController {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTimeDto>> times() {
        List<ReservationTimeDto> responseBody = reservationTimeService.findTimes();
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTimeDto> addTime(@RequestBody ReservationTimeDto reservationTimeDto) {
        ReservationTimeDto responseBody = reservationTimeService.addTime(reservationTimeDto);
        // TODO: dto를 그대로 응답하는 것 괜찮을까?
        return ResponseEntity
                .created(URI.create("/time/" + responseBody.getId()))
                .body(responseBody);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable("id") long id) {
        reservationTimeService.deleteTime(id);
        return ResponseEntity.noContent().build();
    }
}
