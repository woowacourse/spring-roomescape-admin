package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<Member>> readReservations() {
        return ResponseEntity.ok(members);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Member findMember = members.stream()
                .filter(member -> Objects.equals(member.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        members.remove(findMember);

        return ResponseEntity.ok().build();
    }
}
