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
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.controller.mapper.ReservationTimeMapper;
import roomescape.domain.ReservationTime;
import roomescape.service.ReservationService;
import roomescape.service.command.ReservationTimeCreateCommand;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationService service;
    private final ReservationTimeMapper mapper;

    public ReservationTimeController(
            ReservationService service,
            ReservationTimeMapper mapper
    ) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> create(
            @RequestBody ReservationTimeCreateRequest createRequest
    ) {
        ReservationTimeCreateCommand createCommand = mapper.mapCreateToCommand(createRequest);
        ReservationTime createdTime = service.createTime(createCommand);

        ReservationTimeResponse response = mapper.mapToResponse(createdTime);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> findAll() {
        List<ReservationTimeResponse> responses = service.findAllTimes()
                .stream()
                .map(mapper::mapToResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable long id
    ) {
        service.deleteTime(id);

        return ResponseEntity.ok().build();
    }
}
