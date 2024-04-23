package roomescape.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.dto.CreateReservationTimeRequest;
import roomescape.dto.CreateReservationTimeResponse;
import roomescape.dto.FindReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService service;

    public ReservationTimeController(ReservationTimeService service) {
        this.service = service;
    }

    @PostMapping
    public CreateReservationTimeResponse save(@RequestBody CreateReservationTimeRequest request) {
        ReservationTime time = service.save(request);
        return new CreateReservationTimeResponse(time.getId(), time.getStartAt());
    }

    @GetMapping
    public List<FindReservationTimeResponse> findAll() {
        return service.findAll()
            .stream()
            .map(time -> new FindReservationTimeResponse(time.getId(), time.getStartAt()))
            .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
