package roomescape.controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.ReservationResponse;
import roomescape.model.Reservation;

@Controller
public class MainController {

    private final List<Reservation> reservations = new ArrayList<>();

    @GetMapping
    public String readMainPage() {
        return "index";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<ReservationResponse>> readReservations() {
        reservations.add(new Reservation(1L, "브라운", LocalDateTime.of(2023, Month.JANUARY, 1, 10, 0)));
        reservations.add(new Reservation(2L, "브라운", LocalDateTime.of(2023, Month.JANUARY, 2, 11, 0)));

        List<ReservationResponse> list = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(list);
    }
}
