package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationRestController {
    private final ReservationService service;

    public ReservationRestController(ReservationService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReservationResponseDto> getAllReservation() {
        return service.getAllReservation();
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationRequestDto requestDto) {
        ReservationResponseDto responseDto = service.createReservation(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        service.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
