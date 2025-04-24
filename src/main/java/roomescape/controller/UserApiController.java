package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.reservation.dto.ReservationReqDto;
import roomescape.domain.reservation.dto.ReservationResDto;
import roomescape.service.ReservationService;

import java.util.List;

@RestController
public class UserApiController {

    private final ReservationService reservationService;

    public UserApiController(ReservationService reservationService) {
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
