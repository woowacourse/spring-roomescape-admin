package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.entity.Reservation;

@RestController
public class ReservationRestController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1L);

    @GetMapping("/reservations")
    @ResponseBody
    public List<Reservation> readReservation() {
        return reservations;
    }

    @PostMapping("/reservations")
    @ResponseBody
    public Reservation postReservation(@RequestBody ReservationRequestDto requestDto) {
        Long id = index.getAndIncrement();
        Reservation newReservation = requestDto.toEntity(id);
        reservations.add(newReservation);
        return newReservation;
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public void deleteReservation(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
                .filter(r -> Objects.equals(r.getId(), id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        reservations.remove(reservation);
    }
}
