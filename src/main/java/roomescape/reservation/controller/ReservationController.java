package roomescape.reservation.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import roomescape.reservation.dto.CreateReservationResponseDto;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.entity.Reservation;

@Controller
public class ReservationController {

    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    public ReservationController() {
        reservations.add(new Reservation(index.getAndIncrement(), "꾹이", LocalDateTime.now()));
        reservations.add(new Reservation(index.getAndIncrement(), "드라고", LocalDateTime.now()));
    }

    @GetMapping("/admin/reservation")
    public String adminReservationDashboard() {
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationResponseDto>> readAllReservations() {
        List<ReservationResponseDto> allReservations = reservations.stream().map(ReservationResponseDto::toDto)
                .toList();

        return ResponseEntity.ok(allReservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<CreateReservationResponseDto> add(@RequestBody ReservationRequestDto requestDto) {
        LocalDateTime localDateTime = LocalDateTime.of(requestDto.date(), requestDto.time());

        Reservation reservation = new Reservation(index.getAndIncrement(), requestDto.name(), localDateTime);
        reservations.add(reservation);
        CreateReservationResponseDto responseDto = CreateReservationResponseDto.toDto(reservation);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Reservation findReservation = reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        reservations.remove(findReservation);
        return ResponseEntity.noContent().build();
    }
}
