package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.model.Reservation;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final AtomicLong reservationId = new AtomicLong(1); //TODO 이름 변경하기 ㅎ.ㅎ
    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    @ResponseBody
    public List<ReservationResponseDto> getReservations() {
        return reservations.stream()
                .map(ReservationResponseDto::from)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> saveReservation(
            @RequestBody final ReservationRequestDto reservationRequestDto) {
        final Reservation reservation = reservationRequestDto.toEntity(reservationId.getAndIncrement());
        final ReservationResponseDto reservationResponseDto = ReservationResponseDto.from(reservation);
        reservations.add(reservation);
        return ResponseEntity.ok().body(reservationResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(final @PathVariable("id") Long id) {
        final Optional<Reservation> findReservation = reservations.stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findAny();
        findReservation.ifPresent(reservations::remove);
        return ResponseEntity.ok().build();
    }
}
