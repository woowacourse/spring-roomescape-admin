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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationRequestDto;
import roomescape.domain.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final AtomicLong index = new AtomicLong(1);
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("")
    public List<Reservation> readAll() {
        return reservations;
    }

    @PostMapping("")
    public Reservation add(@RequestBody ReservationRequestDto request) {
        Reservation newReservation = new Reservation(
            index.getAndIncrement(), request.name(), request.date(), request.time());
        reservations.add(newReservation);
        return newReservation;
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        Reservation reservation = reservations.stream()
            .filter(it -> Objects.equals(it.getId(), id))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("[ERROR] 예약 번호가 잘못되었습니다."));
        reservations.remove(reservation);
    }
}
