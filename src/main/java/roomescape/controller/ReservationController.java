package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final AtomicLong reservationId = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    @ResponseBody
    public List<ReservationResponseDto> getReservations() {
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> saveReservation(@RequestBody final ReservationRequestDto reservationRequestDto) {
        final Reservation reservation = reservationRequestDto.toEntity(reservationId.getAndIncrement());
        final ReservationResponseDto reservationResponseDto = ReservationResponseDto.from(reservation);
        reservations.add(reservation);
        return ResponseEntity.ok().body(reservationResponseDto);
    }
}
