package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import roomescape.domain.Member;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Controller
public class RoomescapeController {
    private List<Member> members = List.of(new Member(1L, "브라운", LocalDate.of(2023,1,1), LocalTime.of(10,0)));

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/index";
    }

    @GetMapping("/reservations")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Member> readReservations() {
        return members;
    }

    @GetMapping("/admin/reservation")
    public String reservationPage() {
        return "admin/reservation-legacy";
    }
}
