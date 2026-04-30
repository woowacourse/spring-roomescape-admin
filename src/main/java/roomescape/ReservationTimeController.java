package roomescape;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeController(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> create(@RequestBody ReservationTimeCreateDto reservationTimeCreateDto) {
        String time = reservationTimeCreateDto.getStartAt();
        ReservationTime created = reservationTimeRepository.save(time);

        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> findAll() {
        List<ReservationTime> find = reservationTimeRepository.findAll();

        return ResponseEntity.ok(find);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reservationTimeRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
