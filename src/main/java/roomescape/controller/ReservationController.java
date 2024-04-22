package roomescape.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationCreateRequest;

import java.sql.PreparedStatement;
import java.util.List;

@RestController
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> rowMapper;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return ResponseEntity.ok(jdbcTemplate.query(sql, rowMapper));
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationCreateRequest dto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation (name, date, time) values (?, ?, ?)";

        long id = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, dto.name());
            preparedStatement.setString(2, dto.date());
            preparedStatement.setString(3, dto.time());
            return preparedStatement;
        }, keyHolder);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/reservations/" + id)
                .body(dto.createReservation(id));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable(name = "id") long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
