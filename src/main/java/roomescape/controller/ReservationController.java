package roomescape.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

import java.sql.PreparedStatement;
import java.util.List;

@RestController
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("date"),
            rs.getString("time")
    );

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return jdbcTemplate.query("SELECT id, name, date, time FROM reservation", rowMapper);
    }

    @PostMapping("/reservations")
    public Reservation createReservation(@RequestBody ReservationRequest request) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, request.name());
            ps.setString(2, request.date());
            ps.setString(3, request.time());
            return ps;
        }, keyHolder);

        return new Reservation(keyHolder.getKey().longValue(), request.name(), request.date(), request.time());
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
