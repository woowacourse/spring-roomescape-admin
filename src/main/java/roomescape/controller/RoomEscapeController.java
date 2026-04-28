package roomescape.controller;

import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;
import roomescape.dto.RequestDto.ReservationCreateDto;
import roomescape.dto.ResponseDto;

@RequestMapping("/reservations")
@RestController()
public class RoomEscapeController {

    private final Reservations reservations = new Reservations();

    @GetMapping()
    public ResponseEntity<List<ResponseDto.ReservationDto>> findAllReservations() {
        return ResponseEntity.ok(ResponseDto.ReservationDto.getReservationDtos(reservations.findAll()));
    }

    @PostMapping()
    public ResponseEntity<ResponseDto.ReservationDto> createReservation(
            @RequestBody ReservationCreateDto request
    ) {
        Long saveId = reservations.save(request.getName(), request.getDate(), request.getTime());
        Optional<Reservation> foundReservation = reservations.findById(saveId);

        Reservation reservation = foundReservation.orElseThrow(RuntimeException::new);

        return ResponseEntity.ok(ResponseDto.ReservationDto.of(saveId, reservation));
    }
}
