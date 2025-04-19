package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import roomescape.dto.ReservationReqDto;
import roomescape.dto.ReservationResDto;
import roomescape.model.Reservations;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final Reservations reservations = new Reservations();

    @GetMapping
    public ResponseEntity<List<ReservationResDto>> readAll() {
        return ResponseEntity.ok(reservations.getAllReservations());
    }

    @PostMapping
    public ResponseEntity<ReservationResDto> create(@RequestBody ReservationReqDto dto, UriComponentsBuilder ucb) {
        ReservationResDto newReservation = reservations.addAndGet(dto);
        URI uri = ucb.path("reservations/{id}").buildAndExpand(newReservation.id()).toUri();
        return ResponseEntity.created(uri).body(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservations.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
