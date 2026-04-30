package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.reservationtime.TimeQueryingDAO;
import roomescape.reservationtime.ReservationTime;
import roomescape.reservationtime.TimeUpdatingDAO;

import java.util.List;

@Controller
public class ReservationTimeController {

    private final TimeQueryingDAO timeQueryingDAO;
    private final TimeUpdatingDAO timeUpdatingDAO;

    public ReservationTimeController(TimeQueryingDAO timeQueryingDAO, TimeUpdatingDAO timeUpdatingDAO) {
        this.timeQueryingDAO = timeQueryingDAO;
        this.timeUpdatingDAO = timeUpdatingDAO;
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTime reservationTime) {
        Long generatedId = timeUpdatingDAO.insertWithKeyHolder(reservationTime);
        ReservationTime newReservationTime = timeQueryingDAO.findReservationTimeById(generatedId);
        return ResponseEntity.ok().body(newReservationTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> read() {
        List<ReservationTime> reservationTimes = timeQueryingDAO.findAllReservationTime();
        return ResponseEntity.ok().body(reservationTimes);
    }

    @PutMapping("/times/{id}")
    public ResponseEntity<Void> update(@RequestBody ReservationTime newReservationTime, @PathVariable Long id) {
        timeUpdatingDAO.save(id, newReservationTime);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        int delete = timeUpdatingDAO.delete(id);

        if (delete == 0) {
            throw new RuntimeException("삭제하려는 예약 시간을 찾을 수 없습니다.");
        }

        return ResponseEntity.ok().build();
    }
}
