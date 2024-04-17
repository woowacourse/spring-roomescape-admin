package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public List<ReservationResponseDto> reservations() {
        return reservations.stream()
                           .map(ReservationResponseDto::new)
                           .toList();
    }

    @PostMapping("/reservations")
    public ReservationResponseDto create(@RequestBody final ReservationRequestDto request) {
        final LocalTime time = LocalTime.parse(request.getTime(), DateTimeFormatter.ofPattern("HH:mm"));
        final LocalDate date = LocalDate.parse(request.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        final LocalDateTime dateTime = LocalDateTime.of(date, time);

        final Reservation reservation = new Reservation(index.getAndIncrement(), request.getName(), dateTime);
        reservations.add(reservation);

        return new ReservationResponseDto(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        Optional<Reservation> reservation = reservations.stream()
                                                        .filter(it -> it.getId().equals(id))
                                                        .findFirst();
        if(reservation.isPresent()){
            reservations.remove(reservation.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }
}
