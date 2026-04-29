package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.controller.mapper.ReservationTimeMapper;
import roomescape.domain.ReservationTime;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.service.ReservationTimeService;
import roomescape.service.command.ReservationTimeCreateCommand;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;
    private final ReservationTimeMapper reservationTimeMapper;

    public ReservationTimeController(
            ReservationTimeService reservationTimeService,
            ReservationTimeMapper reservationTimeMapper
    ) {
        this.reservationTimeService = reservationTimeService;
        this.reservationTimeMapper = reservationTimeMapper;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> create(
            @RequestBody ReservationTimeCreateRequest createRequest
    ) {
        ReservationTimeCreateCommand createCommand = reservationTimeMapper.mapCreateToCommand(createRequest);
        ReservationTime createdReservationTime = reservationTimeService.create(createCommand);
        ReservationTimeResponse timeResponse = reservationTimeMapper.mapToResponse(createdReservationTime);

        return ResponseEntity.ok(timeResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findAll() {
        List<ReservationTimeResponse> reservationTimes = reservationTimeService.findAll()
                .stream()
                .map(reservationTimeMapper::mapToResponse)
                .toList();

        return ResponseEntity.ok(reservationTimes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable long id
    ) {
        reservationTimeService.delete(id);

        return ResponseEntity.ok().build();
    }
}
