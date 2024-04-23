package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.dto.ReservationTimeResponseDto;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    ReservationController(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping
    public List<ReservationResponseDto> reservations() {
        final String sql = "SELECT \n" +
                "    r.id as reservation_id, \n" +
                "    r.name, \n" +
                "    r.date, \n" +
                "    t.id as time_id, \n" +
                "    t.start_at as time_value \n" +
                "FROM reservation as r \n" +
                "inner join reservation_time as t \n" +
                "on r.time_id = t.id";
        final List<Reservation> reservationList = jdbcTemplate.query(sql, reservationRowMapper);
        return reservationList.stream()
                              .map(ReservationResponseDto::from)
                              .toList();
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(@RequestBody final ReservationRequestDto request) {
        final SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", request.getName())
                .addValue("date", request.getDate())
                .addValue("time_id", request.getTimeId());
        final Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        final String sql = "select * from reservation_time where id = ?";
        final ReservationTimeResponseDto timeResponseDto = ReservationTimeResponseDto.from(
                Objects.requireNonNull(jdbcTemplate.queryForObject(sql, reservationTimeRowMapper, request.getTimeId()))
        );

        return ResponseEntity.ok(new ReservationResponseDto(id, request.getName(), request.getDate(), timeResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        final String sql = "delete from reservation where id = ?";
        final int rowCount = jdbcTemplate.update(sql, Long.valueOf(id));
        if (rowCount > 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        final Reservation reservation = new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getString("time_value")
                )
        );
        return reservation;
    };

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (resultSet, rowNum)
            -> new ReservationTime(resultSet.getLong("id"), resultSet.getString("start_at"));
}
