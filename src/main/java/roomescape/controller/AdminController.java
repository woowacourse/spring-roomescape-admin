package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeResponse;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/reservation")
    public String getReservation() {
        return "admin/reservation";
    }

    @GetMapping("/time")
    public String getTime() {
        return "admin/time";
    }


    @PostMapping("/times")
    public ResponseEntity<Void> createTime() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/times")
    public ResponseEntity<ReservationTimeResponse> getTimes() {
        return null;
    }
}
