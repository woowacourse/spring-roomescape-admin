package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserRoomEscapeController {

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservations() {
        return ResponseEntity.ok(
                List.of(
                        new Reservation(
                                1, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)
                        ),
                        new Reservation(
                                2, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0)
                        )
                )
        );
    }
}
