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
import roomescape.controller.dto.ReservationTimeCreateResponse;
import roomescape.entity.ReservationTime;
import roomescape.service.GameTimeService;

@RequestMapping("/times")
@RestController
public class ReservationTimeController {
    private final GameTimeService gameTimeService;

    public ReservationTimeController(GameTimeService gameTimeService) {
        this.gameTimeService = gameTimeService;
    }

    @GetMapping()
    public ResponseEntity<List<ReservationTimeCreateResponse>> readAllTimes() {
        List<ReservationTimeCreateResponse> gameTimes = gameTimeService.readAll().stream()
                .map(ReservationTimeCreateResponse::from)
                .toList();
        return ResponseEntity.ok().body(gameTimes);
    }

    @PostMapping()
    public ResponseEntity<ReservationTimeCreateResponse> createGameTime(
            @RequestBody ReservationTimeCreateRequest request) {
        ReservationTime saved = gameTimeService.save(request.toEntity());
        return ResponseEntity.ok().body(ReservationTimeCreateResponse.from(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameTime(@PathVariable("id") long id) {
        gameTimeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
