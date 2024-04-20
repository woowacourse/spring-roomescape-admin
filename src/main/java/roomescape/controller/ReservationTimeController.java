package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;

import java.util.List;

@Controller
public class ReservationTimeController {

    @Autowired
    private final ReservationTimeDao reservationTimeDao;

    public @Autowired ReservationTimeController(JdbcTemplate jdbcTemplate) {
        this.reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
    }

    @GetMapping("/admin/time")
    public String time() {
        return "admin/time";
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> insertTime(@RequestBody ReservationTimeRequestDto reservationTimeRequestDto) {
        long timeId = reservationTimeDao.insert(reservationTimeRequestDto);
        return ResponseEntity.ok().body(new ReservationTime(timeId, reservationTimeRequestDto.startAt()));
    }

    @ResponseBody
    @GetMapping("/times")
    public List<ReservationTime> getTimes() {
        return reservationTimeDao.findAll();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        reservationTimeDao.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
