package roomescape.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

import java.sql.PreparedStatement;
import java.util.List;

@RestController
public class ReservationTimeController {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ReservationTime> rowMapper = (rs, rowNum) -> new ReservationTime(
            rs.getLong("id"),
            rs.getString("start_at")
    );

    @GetMapping("/times")
    public List<ReservationTime> getTimes() {
        return jdbcTemplate.query("SELECT id, start_at FROM reservation_time", rowMapper);
    }

    @PostMapping("/times")
    public ReservationTime createTime(@RequestBody ReservationTimeRequest request) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO reservation_time (start_at) VALUES (?)",
                    new String[]{"id"}
            );
            ps.setString(1, request.startAt());
            return ps;
        }, keyHolder);

        return new ReservationTime(keyHolder.getKey().longValue(), request.startAt());
    }

    @DeleteMapping("/times/{id}")
    public void deleteTime(@PathVariable Long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }
}
