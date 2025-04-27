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

    private final ReservationService service;

    public ReservationUserApiController(ReservationService service) {
        this.service = service;
    }

    @GetMapping("reservations")
    public ResponseEntity<List<ReservationResDto>> readAll() {
        List<ReservationResDto> resDtos = service.readAll();
        return ResponseEntity.ok(resDtos);
    }

    @PostMapping("reservations")
    public ResponseEntity<ReservationResDto> add(@RequestBody ReservationReqDto dto) {
        ReservationResDto resDto = service.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @DeleteMapping("reservations/{reservationId}")
    public ResponseEntity<Void> delete(@PathVariable("reservationId") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
