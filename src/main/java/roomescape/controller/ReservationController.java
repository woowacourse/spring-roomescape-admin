package roomescape.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.dto.ReservationResponse;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private static final List<ReservationResponse> RESPONSES = new ArrayList<>();

    static {
        RESPONSES.add(new ReservationResponse(1, "브라운", "2023-01-01", "10:00"));
        RESPONSES.add(new ReservationResponse(2, "브라운", "2023-01-02", "11:00"));
    }

    @ResponseBody
    @GetMapping("/reservations")
    public List<ReservationResponse> findAllReservations() {
        return RESPONSES;
    }
}
