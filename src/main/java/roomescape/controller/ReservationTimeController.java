package roomescape.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.service.ReservationTimeService;
import roomescape.service.dto.ReservationTimeRegisterDto;
import roomescape.service.dto.ReservationTimeResponseDto;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    @ResponseBody
    public ReservationTimeResponseDto registerReservationTime(
            @RequestBody final ReservationTimeRegisterDto reservationTimeRegisterDto) {
        Long savedId = reservationTimeService.saveReservationTime(reservationTimeRegisterDto);
        return new ReservationTimeResponseDto(reservationTimeService.findReservationTimeById(savedId));
    }

    @GetMapping
    @ResponseBody
    public List<ReservationTimeResponseDto> getReservationTimes() {
        return reservationTimeService.findAllReservationTimes();
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") final Long id) {
        try {
            reservationTimeService.deleteReservationTimeById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
