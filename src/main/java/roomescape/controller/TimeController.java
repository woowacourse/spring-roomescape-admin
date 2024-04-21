package roomescape.controller;

import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeResponse;
import roomescape.dto.TimeRequest;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationTime> actorRowMapper = (rs, rowNum) ->
            new ReservationTime(
                    rs.getLong("id"),
                    rs.getTime("start_at").toLocalTime());

    public TimeController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    public ResponseEntity<Long> add(@RequestBody final TimeRequest timeRequest) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setTime(1, Time.valueOf(timeRequest.startAt()));
            return ps;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/times/" + id));
       return ResponseEntity.ok().headers(httpHeaders).body(id); // TODO: create로 바꾸기
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationTimeResponse> findAll() {
        String sql = "SELECT * FROM reservation_time";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, actorRowMapper);
        return ReservationTimeResponse.fromReservationTimes(reservationTimes);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public int delete(@PathVariable(name = "id") final long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
