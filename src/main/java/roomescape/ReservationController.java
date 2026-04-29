package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Controller
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;

    public ReservationController(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation newReservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, newReservation.getName());
            ps.setString(2, newReservation.getDate());
            ps.setString(3, newReservation.getTime());

            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        Reservation reservation = Reservation.toEntity(newReservation, id);

        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        RowMapper<Reservation> rowMapper = (rs, rowNum) -> {
            Reservation reservation = new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getString("time")
            );
            return reservation;
        };

        String sql = "select * from reservation";
        List<Reservation> reservations = jdbcTemplate.query(sql, rowMapper);

        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String sql = "delete from reservation where id = ?";

        jdbcTemplate.update(sql, id);

        return ResponseEntity.ok().build();
    }
}
