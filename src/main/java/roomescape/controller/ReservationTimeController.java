package roomescape.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.entity.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@RestController
public class ReservationTimeController {

    @Autowired
    ReservationTimeRepository repository;

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> readAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> create(
            @RequestBody ReservationTimeRequestDto reservationTimeRequestDto
    ) {
        ReservationTime reservationTime = new ReservationTime(reservationTimeRequestDto.startAt());
        return ResponseEntity.ok(repository.save(reservationTime));
    }

    @DeleteMapping("times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repository.delete(id);
        return ResponseEntity.ok().build();
    }
}
