package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import roomescape.domain.Member;


@Controller
public class RoomescapeController {
    private List<Member> members = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/reservation")
    public String reservationPage() {
        return "admin/reservation-legacy";
    }

    @PostMapping("/reservations")
    public ResponseEntity<Member> createReservation(@RequestBody Member member) {
        Member newMember = Member.toEntity(index.incrementAndGet(), member);
        members.add(newMember);
        return ResponseEntity.ok(newMember);
    }

    @GetMapping("/reservations")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Member> readReservations() {
        return members;
    }
}
