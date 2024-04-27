package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.request.TimesRequest;
import roomescape.dto.response.TimesResponse;
import roomescape.service.RoomescapeService;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimesController {

    private final RoomescapeService roomescapeService;

    public TimesController(RoomescapeService roomescapeService) {
        this.roomescapeService = roomescapeService;
    }

    @GetMapping
    public List<TimesResponse> times() {
        return roomescapeService.findAllTimes();
    }

    @PostMapping
    public TimesResponse addTimes(@RequestBody TimesRequest timesRequest) {
        return roomescapeService.addTime(timesRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public int deleteTimes(@PathVariable Long id) {
        return roomescapeService.removeTime(id);
    }
}
