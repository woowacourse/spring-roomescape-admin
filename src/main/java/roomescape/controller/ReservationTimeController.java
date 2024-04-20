package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.service.ReservationTimeService;

import java.util.List;

@Controller
public class ReservationTimeController {

    @Autowired
    private ReservationTimeService reservationTimeService;

    @GetMapping("/admin/time")
    public String time() {
        return "admin/time";
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> insertTime(@RequestBody ReservationTimeRequestDto reservationTimeRequestDto) {
        ReservationTime reservationTime = reservationTimeService.insertReservationTime(reservationTimeRequestDto);
        return ResponseEntity.ok().body(reservationTime);
    }

    @ResponseBody
    @GetMapping("/times")
    public List<ReservationTime> getTimes() {
        return reservationTimeService.getAllReservationTimes();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationTimeService.deleteReservationTime(id);
        return ResponseEntity.ok().build();
    }
}
