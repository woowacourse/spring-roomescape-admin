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
import roomescape.controller.dto.GameTimeCreateRequest;
import roomescape.controller.dto.GameTimeCreateResponse;
import roomescape.entity.GameTime;
import roomescape.service.GameTimeService;

@RequestMapping("/times")
@RestController
public class GameTimeController {
    private final GameTimeService gameTimeService;

    public GameTimeController(GameTimeService gameTimeService) {
        this.gameTimeService = gameTimeService;
    }

    @GetMapping()
    public ResponseEntity<List<GameTimeCreateResponse>> readAllTimes() {
        List<GameTimeCreateResponse> availableTimes = gameTimeService.readAll().stream()
                .map(GameTimeCreateResponse::from)
                .toList();
        return ResponseEntity.ok().body(availableTimes);
    }

    @PostMapping()
    public ResponseEntity<GameTimeCreateResponse> createAvailableTime(
            @RequestBody GameTimeCreateRequest request) {
        GameTime saved = gameTimeService.save(request.toEntity());
        return ResponseEntity.ok().body(GameTimeCreateResponse.from(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvailableTime(@PathVariable("id") long id) {
        gameTimeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
