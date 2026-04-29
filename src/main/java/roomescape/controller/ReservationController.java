package roomescape.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
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
            rs.getLong("reservation_id"),
            rs.getString("name"),
            rs.getString("date"),
            new ReservationTime(rs.getLong("time_id"), rs.getString("start_at"))
    );

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        return jdbcTemplate.query(
                "SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at " +
                "FROM reservation r INNER JOIN reservation_time t ON r.time_id = t.id",
                rowMapper
        );
    }

    @PostMapping("/reservations")
    public Reservation createReservation(@RequestBody ReservationRequest request) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, request.name());
            ps.setString(2, request.date());
            ps.setLong(3, request.timeId());
            return ps;
        }, keyHolder);

        final ReservationTime time = jdbcTemplate.queryForObject(
                "SELECT id, start_at FROM reservation_time WHERE id = ?",
                (rs, rowNum) -> new ReservationTime(rs.getLong("id"), rs.getString("start_at")),
                request.timeId()
        );

        return new Reservation(keyHolder.getKey().longValue(), request.name(), request.date(), time);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
