package roomescape.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.entity.Reservation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final List<Reservation> reservations;
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping
    @ResponseBody
    public List<Reservation> getReservations() {
        return Collections.unmodifiableList(reservations);
    }

    @PostMapping
    @ResponseBody
    public ReservationResponseDto add(
        @RequestBody ReservationRequestDto reservationRequestDto) {
        final Reservation reservation = Reservation.from(index.incrementAndGet(), reservationRequestDto);
        reservations.add(reservation);

        return ReservationResponseDto.from(reservation);
    }
}
