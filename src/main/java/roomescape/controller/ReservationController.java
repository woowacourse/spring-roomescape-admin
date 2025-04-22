package roomescape.controller;

import jakarta.validation.Valid;
import java.net.URI;
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
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.AddReservationDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationTimeService reservationTimeService;

    public ReservationController(ReservationService reservationService, ReservationTimeService reservationTimeService) {
        this.reservationService = reservationService;
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> reservations() {
        List<Reservation> reservations = reservationService.allReservations();
        List<ReservationResponseDto> reservationDtos = reservations.stream()
                .map((reservation) -> new ReservationResponseDto(reservation.getId(), reservation.getName(),
                        reservation.getTime().getTime(), reservation.getDate()))
                .toList();
        return ResponseEntity.ok(reservationDtos);
    }

    @PostMapping
    public ResponseEntity<Void> addReservations(@RequestBody @Valid AddReservationDto newReservationDto) {
        LocalTime time = newReservationDto.time();
        ReservationTime reservationTime = new ReservationTime(null, time);
        Long addedReservationTime = reservationTimeService.addReservationTime(reservationTime);
        
        Reservation newReservation = newReservationDto.toReservation(addedReservationTime);
        long addedReservationId = reservationService.addReservation(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + addedReservationId)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservations(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
