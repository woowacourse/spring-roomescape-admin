package roomescape.reservation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.reservation.domain.dto.ReservationReqDto;
import roomescape.reservation.domain.dto.ReservationResDto;
import roomescape.reservation.service.ReservationService;

import java.util.List;

@RestController
public class ReservationUserApiController {

    private final ReservationService reservationService;

    public ReservationUserApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("reservations")
    public ResponseEntity<List<ReservationResDto>> readReservations() {
        List<ReservationResDto> resDtos = reservationService.readAll();
        return ResponseEntity.ok(resDtos);
    }

    @PostMapping("reservations")
    public ResponseEntity<ReservationResDto> add(@RequestBody ReservationReqDto dto) {
        ReservationResDto resDto = reservationService.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @DeleteMapping("reservations/{reservationId}")
    public ResponseEntity<Void> delete(@PathVariable("reservationId") Long id) {
       reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
