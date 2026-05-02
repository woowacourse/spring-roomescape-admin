package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.service.ReservationService;
import roomescape.dto.ReservationCreateReqDto;
import roomescape.dto.ReservationResDto;
import roomescape.service.command.ReservationCommand;

import java.util.List;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResDto> createReservation(@RequestBody ReservationCreateReqDto dto) {
        ReservationCommand command = new ReservationCommand(
                dto.getName(),
                dto.getDate(),
                dto.getTimeId()
        );
        ReservationResDto reservation = reservationService.createReservation(command);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResDto>> getReservations() {
        List<ReservationResDto> reservations = reservationService.getReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResDto> getReservationById(@PathVariable Long id) {
        ReservationResDto reservation = reservationService.getReservationById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationResDto> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
