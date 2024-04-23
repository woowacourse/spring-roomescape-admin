package roomescape.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequestDto;
import roomescape.dto.ReservationTimeResponseDto;

import javax.sql.DataSource;
import java.util.List;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeController(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("RESERVATION_TIME")
                .usingGeneratedKeyColumns("id");
    }

    @PostMapping
    public ReservationTimeResponseDto create(@RequestBody final ReservationTimeRequestDto request) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("start_at", request.getStartAt());
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new ReservationTimeResponseDto(id, request.getStartAt());
    }

    @GetMapping
    public List<ReservationTimeResponseDto> retrieveTimes() {
        List<ReservationTime> reservationTimeList = jdbcTemplate.query("select * from reservation_time", timeRowMapper);
        return reservationTimeList.stream()
                                  .map(ReservationTimeResponseDto::from)
                                  .toList();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, Long.valueOf(id));
    }

    private final RowMapper<ReservationTime> timeRowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );
        return reservationTime;
    };
}
