package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.CreateReservationTimeRequest;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeRepository repository;

    public ReservationTimeController(ReservationTimeRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ReservationTime save(@RequestBody CreateReservationTimeRequest request) {
        return repository.save(request);
    }

    @GetMapping
    public List<ReservationTime> readAll() {
        return repository.findAll();
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        repository.delete(id);
    }
}
