package roomescape.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRepository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateDto;
import roomescape.dto.ReservationResponseDto;

@RestController
public class ReservationController {
    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> readReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponseDto> responseDtos = new ArrayList<>();
        reservations.forEach(reservation -> responseDtos.add(ReservationResponseDto.from(reservation)));
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationCreateDto createDto) {
        Reservation reservation = new Reservation(0L, createDto.getName(), createDto.getDate(),
                new ReservationTime(createDto.getTimeId(), null));
        Reservation createdReservation = reservationRepository.create(reservation);
        ReservationResponseDto responseDto = ReservationResponseDto.from(createdReservation);
        URI reservationURI = URI.create("/reservations/" + responseDto.getId());
        return ResponseEntity.created(reservationURI).body(responseDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        Reservation findReservation = reservationRepository.findById(id);
        reservationRepository.remove(findReservation);
        return ResponseEntity.noContent().build();
    }
}
