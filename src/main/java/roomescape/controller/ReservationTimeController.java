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
import roomescape.controller.dto.FindReservationTimeResponse;
import roomescape.controller.dto.SaveReservationTimeRequest;
import roomescape.controller.dto.SaveReservationTimeResponse;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationTimeService;
import roomescape.service.dto.SaveReservationTimeDto;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService service;

    public ReservationTimeController(ReservationTimeService service) {
        this.service = service;
    }

    @PostMapping
    public SaveReservationTimeResponse save(@RequestBody SaveReservationTimeRequest request) {
        ReservationTime time = service.save(new SaveReservationTimeDto(request.startAt()));
        return new SaveReservationTimeResponse(time.getId(), time.getStartAt());
    }

    @GetMapping
    public List<FindReservationTimeResponse> findAll() {
        return service.findAll()
            .stream()
            .map(time -> new FindReservationTimeResponse(time.getId(), time.getStartAt()))
            .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
