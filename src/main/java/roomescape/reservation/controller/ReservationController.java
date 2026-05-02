package roomescape.reservation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.controller.dto.ReservationRequestDto;
import roomescape.reservation.controller.dto.ReservationResponseDto;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.service.ReservationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> readAll() {
        List<ReservationResponseDto> responses = reservationService.findAll()
                .stream()
                .map(ReservationResponseDto::from)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(@RequestBody ReservationRequestDto requestDto) {
        Reservation reservation = reservationService.save(
                requestDto.name(),
                requestDto.date(),
                requestDto.timeId()
        );

        ReservationResponseDto response = ReservationResponseDto.from(reservation);
        return ResponseEntity
                .created(URI.create("/reservations/" + response.id()))
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
