package roomescape.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import roomescape.ReservationService;
import roomescape.controller.dto.ReservationDto;
import roomescape.controller.dto.ReservationRegisterDto;

@RestController
@RequestMapping("/reservations")
public class RoomescapeController {

    private final ReservationService reservationService;

    public RoomescapeController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDto> getReservations() {
        return reservationService.findAllReservations();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ReservationDto registerReservation(
            @RequestBody @Valid final ReservationRegisterDto reservationRegisterDto) {
        Long savedId = reservationService.saveReservation(reservationRegisterDto);
        return reservationService.findReservationById(savedId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable(name = "id") final long id) {
        try {
            reservationService.deleteReservationById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        }
    }
}
