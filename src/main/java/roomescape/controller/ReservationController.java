package roomescape;

import java.sql.PreparedStatement;
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
import java.util.List;

@RestController
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;
    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    public List<Reservation> list() {
        String sql = """
                SELECT
                    r.id AS reservation_id,
                    r.name AS reservation_name,
                    r.date AS reservation_date,
                    t.id AS time_id,
                    t.start_at AS time_start_at
                FROM reservation r
                INNER JOIN reservation_time t ON r.time_id = t.id
                """;

        RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("reservation_name"),
                rs.getString("reservation_date"),
                new ReservationTime(
                        rs.getLong("time_id"),
                        rs.getString("time_start_at")
                )
        );
        return jdbcTemplate.query(sql, rowMapper);
    }

    @PostMapping("/reservations")
    public Reservation create(@RequestBody ReservationRequest request) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, request.getName());
            ps.setString(2, request.getDate());
            ps.setLong(3, request.getTimeId());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        ReservationTime time = jdbcTemplate.queryForObject(
                "SELECT id, start_at FROM reservation_time WHERE id = ?",
                (rs, rowNum) -> new ReservationTime(rs.getLong("id"), rs.getString("start_at")),
                request.getTimeId()
        );


        return new Reservation(id, request.getName(), request.getDate(), time);
    }

    @DeleteMapping("/reservations/{id}")
    public void delete(@PathVariable Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }

}
