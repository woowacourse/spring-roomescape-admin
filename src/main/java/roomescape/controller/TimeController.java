package roomescape.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Time;
import roomescape.dto.TimeRequestDto;
import roomescape.dto.TimeResponseDto;

import javax.sql.DataSource;
import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeController(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("RESERVATION_TIME")
                .usingGeneratedKeyColumns("id");
    }

    @PostMapping
    public TimeResponseDto create(@RequestBody final TimeRequestDto request) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("start_at", request.getStartAt());
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new TimeResponseDto(id, request.getStartAt());
    }

    @GetMapping
    public List<TimeResponseDto> retrieveTimes() {
        List<Time> timeList = jdbcTemplate.query("select * from reservation_time", timeRowMapper);
        return timeList.stream()
                       .map(TimeResponseDto::from)
                       .toList();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }

    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );
        return time;
    };
}
