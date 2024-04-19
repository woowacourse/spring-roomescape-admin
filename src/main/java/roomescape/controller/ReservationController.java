package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationRequestDto;
import roomescape.controller.dto.ReservationResponseDto;
import roomescape.entity.Reservation;
import roomescape.service.ReservationService;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping()
    public ResponseEntity<List<ReservationResponseDto>> readAllReservations() {
        List<ReservationResponseDto> reservations = reservationService.readAll().stream()
                .map(ReservationResponseDto::from)
                .toList();
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping()
    public ResponseEntity<ReservationResponseDto> createReservation(
            @RequestBody ReservationRequestDto reservationRequestDto) {

        String name = reservationRequestDto.getName();
        LocalDate date = reservationRequestDto.getDate();
        LocalTime time = reservationRequestDto.getTime();

        Reservation newReservation = new Reservation(name, date, time);
        Reservation savedReservation = reservationService.saveReservation(newReservation);
        return ResponseEntity.ok().body(ReservationResponseDto.from(savedReservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable("id") long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
