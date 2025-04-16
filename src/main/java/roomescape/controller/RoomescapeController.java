package roomescape.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.CreateReservationDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.exception.InvalidReservationException;
import roomescape.repository.ReservationRepository;

@RestController
public class RoomescapeController {

    private final ReservationRepository reservationRepository;

    public RoomescapeController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDto>> getReservations(){
        List<ReservationResponseDto> reservations = reservationRepository.findAll()
                .stream()
                .map(ReservationResponseDto::toDto)
                .toList();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody CreateReservationDto createReservationDto) {
        Reservation reservation = reservationRepository.add(createReservationDto);
        return ResponseEntity.ok().body(ReservationResponseDto.toDto(reservation));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        try {
            reservationRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (InvalidReservationException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
