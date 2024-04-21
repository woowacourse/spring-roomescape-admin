package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import javax.sql.DataSource;
import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    ReservationController(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping("/reservations")
    public List<ReservationResponseDto> reservations() {
        final String sql = "select id, name, date, time from reservation";
        final List<Reservation> reservationList = jdbcTemplate.query(sql, reservationRowMapper);
        return reservationList.stream()
                              .map(ReservationResponseDto::from)
                              .toList();
    }

    @GetMapping("/reservations/{id}")
    public ReservationResponseDto reservation(@PathVariable final Long id) {
        final String sql = "select id, name, date, time from reservation where id = ?";
        final Reservation reservation = jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
        return ReservationResponseDto.from(reservation);
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponseDto> create(@RequestBody final ReservationRequestDto request) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", request.getName())
                .addValue("date", request.getDate())
                .addValue("time", request.getTime());
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        String sql = "delete from reservation where id = ?";
        int rowCount = jdbcTemplate.update(sql, Long.valueOf(id));
        if (rowCount > 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
        return reservation;
    };
}
