package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.model.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

@RestController
@RequestMapping("/reservations")
public final class ReservationApiController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicInteger id = new AtomicInteger(1);

    @GetMapping
    public List<ReservationResponseDto> reservations() {
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    @PostMapping
    public ReservationResponseDto reserve(@RequestBody ReservationRequestDto dto) {
        Reservation reservation = dto.toEntity(id.getAndIncrement());
        reservations.add(reservation);
        return ReservationResponseDto.from(reservation);
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable int id) {
        reservations.removeIf(reservation -> reservation.getId() == id);
    }
}
