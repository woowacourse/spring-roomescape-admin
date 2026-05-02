package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationTimeCreateReqDto;
import roomescape.dto.ReservationTimeResDto;
import roomescape.service.ReservationTimeService;
import roomescape.service.command.ReservationTimeCommand;

import java.util.List;

@RequestMapping("/times")
@RestController
public class ReservationTimeController {

    private final ReservationTimeService timeService;

    public ReservationTimeController(ReservationTimeService timeService) {
        this.timeService = timeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResDto> createTime(@RequestBody ReservationTimeCreateReqDto dto) {
        ReservationTimeCommand command = new ReservationTimeCommand(dto.getStartAt());
        ReservationTimeResDto timeResDto = timeService.createTime(command);
        return new ResponseEntity<>(timeResDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResDto>> getTimes() {
        List<ReservationTimeResDto> timeResDtos = timeService.getTimes();
        return new ResponseEntity<>(timeResDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationTimeResDto> deleteTime(@PathVariable Long id) {
        timeService.deleteTime(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
