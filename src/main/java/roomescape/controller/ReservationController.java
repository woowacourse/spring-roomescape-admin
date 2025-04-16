package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.dto.response.ReservationFindResponse;

@Controller
public class ReservationController {

    private final List<ReservationFindResponse> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @GetMapping("/admin/reservation")
    public String getReservation() {
        return "admin/reservation-legacy";
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationFindResponse>> getReservations() {
        ReservationFindResponse reservation1 = new ReservationFindResponse(1L, "fora", null, null);
        ReservationFindResponse reservation2 = new ReservationFindResponse(2L, "aa", null, null);
        ReservationFindResponse reservation3 = new ReservationFindResponse(3L, "bb", null, null);

        reservations.add(reservation1);
        reservations.add(reservation2);
        reservations.add(reservation3);
        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationFindResponse> createReservation(@RequestBody Map<String, String> resultMap) {

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
}
