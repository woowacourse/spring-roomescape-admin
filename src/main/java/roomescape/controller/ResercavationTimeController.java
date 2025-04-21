package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import roomescape.dto.ReservationTimeReadDto;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Controller
@RequestMapping("times")
public class ResercavationTimeController {
    private final ReservationTimeRepository timeRepository;

    public ResercavationTimeController(ReservationTimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeReadDto> createReservationTime(@RequestBody ReservationTime time) {
        ReservationTimeReadDto readDto = timeRepository.add(time);
        return ResponseEntity.created(URI.create("times/" + readDto.getId())).body(readDto);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeReadDto>> getReservationTimes() {
        return ResponseEntity.ok(timeRepository.findAll());
    }
}
