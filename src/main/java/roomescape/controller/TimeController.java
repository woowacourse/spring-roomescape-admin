package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.domain_entity.ReservationTime;
import roomescape.dto.TimeRequestDto;
import roomescape.service.TimeService;

@Controller
public class TimeController {

    @Autowired
    private TimeService timeService;

    @GetMapping("/admin/time")
    public String displayAdminTime() {
        return "/admin/time.html";
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createTime(
            @RequestBody TimeRequestDto timeRequest
    ) {
        ReservationTime reservationTime = timeService.createTime(timeRequest);
        return ResponseEntity.ok().body(reservationTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> getTimes(
    ) {
        List<ReservationTime> reservationTimes = timeService.findAllTimes();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(
            @PathVariable("id") long idRequest
    ) {
        timeService.deleteTime(idRequest);
        return ResponseEntity.ok().build();
    }
}
