package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDto;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Reservation> rowMapper =
        (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping()
    public ResponseEntity<List<Reservation>> read() {
        String sql = "select id, name, date, time from reservation";
        List<Reservation> reservations = jdbcTemplate.query(sql, rowMapper);

        return ResponseEntity.ok(reservations);
    }

    @PostMapping()
    public ResponseEntity<Reservation> create(@RequestBody ReservationDto reservationDto) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", reservationDto.name())
                .addValue("date", reservationDto.date())
                .addValue("time", reservationDto.time());
        Long id = jdbcInsert.executeAndReturnKey(parameterSource).longValue();
        Reservation newReservation = reservationDto.toEntity(id);

        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.noContent().build();
    }
}
