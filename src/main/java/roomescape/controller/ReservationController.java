package roomescape.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Controller
public class ReservationController {
    // TODO: 하나씩 수정하기 위한 잔여 코드 => 필히 삭제해줘야 함.
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    private JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    @ResponseBody
    public List<ReservationResponse> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, date, time FROM reservation ORDER BY id",
                (rs, rowNum) -> {
                    Reservation reservation = new Reservation(
                            rs.getLong("id"),
                            new Name(rs.getString("name")),
                            new ReservationDate(rs.getString("date")),
                            new ReservationTime(rs.getString("time"))
                    );

                    return ReservationResponse.from(reservation);
                }
        );
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ReservationResponse create(@RequestBody ReservationRequest request) {
        Long id = index.getAndIncrement();

        Reservation reservation = new Reservation(
                id,
                new Name(request.name()),
                new ReservationDate(request.date()),
                new ReservationTime(request.time())
        );

        reservations.add(reservation);

        return ReservationResponse.from(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
