package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ReservationController {
    private final ConcurrentHashMap<Long, Reservation> reservations = new ConcurrentHashMap<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/reservations")
    public List<ReservationResponseDto> reservations() {
        return reservations.values()
                           .stream()
                           .map(ReservationResponseDto::new)
                           .toList();
    }

    @PostMapping("/reservations")
    public ReservationResponseDto create(@RequestBody final ReservationRequestDto request) {
        final LocalTime time = LocalTime.parse(request.getTime(), DateTimeFormatter.ofPattern("HH:mm"));
        final LocalDate date = LocalDate.parse(request.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        final LocalDateTime dateTime = LocalDateTime.of(date, time);

        final Long id = index.getAndIncrement();
        final Reservation reservation = new Reservation(id, request.getName(), dateTime);
        reservations.put(id, reservation);

        return new ReservationResponseDto(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        if (reservations.containsKey(id)) {
            reservations.remove(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
