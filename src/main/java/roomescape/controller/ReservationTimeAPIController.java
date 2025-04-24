package roomescape.controller;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@RestController
@RequestMapping("/times")
public class ReservationTimeAPIController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping
    public ResponseEntity<ReservationTime> addTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        String sql = "insert into reservation_time(start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTimeRequest.getStartAt().toString());
            return ps;
        }, keyHolder);
        ReservationTime reservationTime = new ReservationTime(
                keyHolder.getKey().longValue(),
                reservationTimeRequest.getStartAt()
        );
        return ResponseEntity.ok(reservationTime);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> searchTime() {
        String sql = "select id, start_at from reservation_time";
        List<ReservationTime> times = jdbcTemplate.query(sql,
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        LocalTime.parse(rs.getString("start_at"))
                ));
        return ResponseEntity.ok(times);
    }
}
