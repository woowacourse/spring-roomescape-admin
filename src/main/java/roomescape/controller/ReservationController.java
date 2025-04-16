package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.response.ReservationFindResponse;

@Controller
public class ReservationController {

    private final List<ReservationFindResponse> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/admin/reservation")
    public String getReservationPage() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationFindResponse>> findAll() {
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationFindResponse> create(@RequestBody Map<String, String> resultMap) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate date = LocalDate.parse(resultMap.get("date"), dateFormatter);
        LocalTime time = LocalTime.parse(resultMap.get("time"), timeFormatter);

        ReservationFindResponse reservationFindResponse = new ReservationFindResponse(index.getAndIncrement(),
                resultMap.get("name"),
                date,
                time);
        reservations.add(reservationFindResponse);
        return ResponseEntity.ok().body(reservationFindResponse);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ReservationFindResponse reservationFindResponse = reservations.stream()
                .filter(it -> Objects.equals(it.id(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        reservations.remove(reservationFindResponse);

        return ResponseEntity.noContent().build();
    }
}
