package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservations;
import roomescape.dto.ResponseDto;

@RequestMapping("/reservations")
@RestController()
public class RoomEscapeController {

    private final Reservations reservations = new Reservations();

    @GetMapping()
    public ResponseEntity<List<ResponseDto.Reservation>> findAllReservations() {
        return ResponseEntity.ok(ResponseDto.Reservation.getReservationDtos(reservations.findAll()));
    }
}
