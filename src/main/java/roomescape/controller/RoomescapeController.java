package roomescape.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.ReservationCreateDto;
import roomescape.dto.ReservationReadDto;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

@Controller
public class RoomescapeController {
    private final ReservationRepository reservationRepository;

    public RoomescapeController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationReadDto>> getReservations() {
        return ResponseEntity.ok().body(reservationRepository.findAll());
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationReadDto> createReservation(@RequestBody ReservationCreateDto dto) {
        Reservation newReservation = new Reservation(dto.getName(), dto.getDate(), dto.getTime());
        ReservationReadDto readDto = reservationRepository.add(newReservation);
        return ResponseEntity.created(URI.create("reservations/" + readDto.getId())).body(readDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationRepository.deleteBy(id);
        return ResponseEntity.ok().build();
    }
}
