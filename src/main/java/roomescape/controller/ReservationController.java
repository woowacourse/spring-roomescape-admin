package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.ReservationDto;
import roomescape.entity.Reservation;
import roomescape.repository.ReservationRepository;

@Controller
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> reservations() {
        List<Reservation> reservations = reservationRepository.findAllReservations();
        List<ReservationDto> responseBody = reservations.stream()
                .map(ReservationDto::from)
                .toList();
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDto> addReservation(@RequestBody ReservationDto reservationDto) {
        long id = reservationRepository.save(reservationDto.toEntity());
        ReservationDto responseBody = new ReservationDto(
                id,
                reservationDto.getName(),
                reservationDto.getDate(),
                reservationDto.getTime()
        );
        return ResponseEntity
                .created(URI.create("/reservations/" + id))
                .body(responseBody);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") long id) {
        reservationRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
