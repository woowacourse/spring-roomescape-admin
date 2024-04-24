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
import roomescape.domain.TimeSlot;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final TimeController timeController;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Reservation> rowMapper =
        (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
                resultSet.getString("name"),
                LocalDate.parse(resultSet.getString("date")),
                new TimeSlot(resultSet.getLong("time_id"), resultSet.getString("time_value"))
        );


    public ReservationController(TimeController timeController, JdbcTemplate jdbcTemplate) {
        this.timeController = timeController;
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time_id")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping()
    public ResponseEntity<List<Reservation>> read() {
        String sql = """
                SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
                FROM reservation as r inner join reservation_time as t
                on r.time_id = t.id
                """;
        List<Reservation> reservations = jdbcTemplate.query(sql, rowMapper);

        return ResponseEntity.ok(reservations);
    }

    @PostMapping()
    public ResponseEntity<Reservation> create(@RequestBody ReservationDto reservationDto) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", reservationDto.name())
                .addValue("date", reservationDto.date())
                .addValue("time_id", reservationDto.timeId());
        Long id = jdbcInsert.executeAndReturnKey(parameterSource).longValue();
        Reservation newReservation = reservationDto.toEntity(id, timeController.findById(reservationDto.timeId()));

        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.noContent().build();
    }
}
