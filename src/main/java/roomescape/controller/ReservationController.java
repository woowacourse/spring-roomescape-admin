package roomescape.controller;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private final AtomicLong index = new AtomicLong(0);

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping("/admin")
    public String getAdminPage() {
        return "/admin/index";
    }

    @GetMapping("/admin/reservation")
    public String getReservationPage(Model model) {
        List<ReservationResponse> reservationResponses = reservations.stream().map(ReservationResponse::fromEntity).toList();
        model.addAttribute("reservationDtos", reservationResponses);
        return "/admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        List<ReservationResponse> reservationResponses = reservations.stream().map(ReservationResponse::fromEntity).toList();
        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservations(@RequestBody ReservationCreateRequest reservationCreateDto) {
        Reservation reservation = reservationCreateDto.toEntity(index.incrementAndGet());
        reservations.add(reservation);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        Reservation found = reservations.stream().filter(reservation -> Objects.equals(reservation.getId(), id))
                .findFirst().orElseThrow(() -> new IllegalArgumentException(id + "는 존재하지 않습니다."));

        reservations.remove(found);
        return ResponseEntity.ok().build();
    }
}
