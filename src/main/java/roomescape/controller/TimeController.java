package roomescape.controller;

import java.sql.Time;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeRequest;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationTime> rowMapper = (rs, rowNum) ->
            new ReservationTime(
                    rs.getLong("id"),
                    rs.getTime("start_at").toLocalTime());

    public TimeController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void add(@RequestBody final TimeRequest timeRequest) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        jdbcTemplate.update(sql, Time.valueOf(timeRequest.startAt()));
    }
}
