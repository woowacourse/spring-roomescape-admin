package roomescape.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Person;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.Reservations;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final Reservations reservations = new Reservations();
    private final AtomicLong reservationIndex = new AtomicLong(1);
    private final AtomicLong personIndex = new AtomicLong(1);

    @GetMapping("")
    public List<ReservationResponseDto> readReservations() {
        return reservations.getReservations().stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    @PostMapping("")
    public Reservation createReservations(
            @RequestBody ReservationRequestDto reservationRequestDto) {
        Person person = new Person(personIndex.getAndIncrement(), reservationRequestDto.name());
        ReservationTime reservationTime = new ReservationTime(
                LocalDateTime.of(reservationRequestDto.date(), reservationRequestDto.time()));
        return reservations.save(person, reservationTime);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable(name = "id") Long id) {
        reservations.deleteById(id);
    }
}
