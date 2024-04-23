package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationCreateRequest;

import java.sql.PreparedStatement;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> rowMapper;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getString("start_at"))
        );
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readReservations() {
        String sql = """
                SELECT reservation.id, reservation.name, reservation.date, reservation.time_id, reservation_time.start_at
                FROM reservation
                JOIN reservation_time ON reservation.time_id = reservation_time.id;
                """;
        return ResponseEntity.ok(jdbcTemplate.query(sql, rowMapper));
    }

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody ReservationCreateRequest dto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation (name, date, time_id) values (?, ?, ?)";

        long id = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, dto.name());
            preparedStatement.setString(2, dto.date());
            preparedStatement.setLong(3, dto.timeId());
            return preparedStatement;
        }, keyHolder);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + id)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
