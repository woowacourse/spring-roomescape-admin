package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;

import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@RestController
public class ReservationController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) -> {
        Reservation reservation = new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                rs.getString("time")
        );
        return reservation;
    };

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservationDatum() {
        List<Reservation> reservations = jdbcTemplate.query("SELECT * FROM reservation", reservationRowMapper);
        return ResponseEntity.ok(reservations);
    }


    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservationData(@RequestBody final Reservation request) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection con) -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, request.getName());
            preparedStatement.setString(2, request.getDate());
            preparedStatement.setString(3, request.getTime());

            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return ResponseEntity.created(URI.create("/reservations/" + id))
                .body(new Reservation(id, request));


    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservationsData(@PathVariable final Long id) {
        String sql = "DELETE FROM reservation WHERE id = (?)";
        int updateCount = jdbcTemplate.update(sql, id);

        if (updateCount == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
