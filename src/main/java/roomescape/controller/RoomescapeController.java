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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.ReservationService;
import roomescape.controller.dto.ReservationDto;
import roomescape.controller.dto.ReservationRegisterDto;
import roomescape.repository.ReservationRepository;

@RestController
public class RoomescapeController {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    public RoomescapeController(ReservationRepository reservationRepository, ReservationService reservationService) {
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public List<ReservationDto> getReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationDto::new)
                .toList();
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ReservationDto registerReservation(
            @RequestBody @Valid final ReservationRegisterDto reservationRegisterDto) {
        Long savedId = reservationService.saveReservation(reservationRegisterDto);
        return reservationService.findReservationById(savedId);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable(name = "id") final long id) {
        try {
            reservationRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
