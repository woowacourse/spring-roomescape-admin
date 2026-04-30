package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequest;

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
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest reservationRequest) {
        String sql = "insert into reservation (name, date, time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationRequest.name());
            ps.setString(2, reservationRequest.date());
            ps.setLong(3, reservationRequest.timeId());

            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        ReservationTime reservationTime = findReservationTimeByTimeId(reservationRequest.timeId());
        Reservation reservation = reservationRequest.toEntity(reservationTime, id);

        return ResponseEntity.ok(reservation);
    }

    private ReservationTime findReservationTimeByTimeId(Long timeId) {
        String sql = "select id, start_at from reservation_time where id = ?";

        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> {
                    ReservationTime reservationTime = new ReservationTime(
                            rs.getLong("id"),
                            rs.getString("start_at")
                    );
                    return reservationTime;
                }, timeId);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        RowMapper<Reservation> rowMapper = (rs, rowNum) -> {
            Reservation reservation = new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getLong("time_id"),
                    rs.getString("time_value")
            );
            return reservation;
        };

        String sql = """
                    SELECT
                        r.id as reservation_id,
                        r.name,
                        r.date,
                        t.id as time_id,
                        t.start_at as time_value
                    FROM reservation as r INNER JOIN reservation_time as t ON r.time_id = t.id
        """;
        List<Reservation> reservations = jdbcTemplate.query(sql, rowMapper);

        return ResponseEntity.ok(reservations);
    }
    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createReservationTime(@RequestBody ReservationTime newReservationTime) {
        String sql = "insert into reservation_time (start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, newReservationTime.getStartAt());

            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        ReservationTime reservationTime = ReservationTime.toEntity(newReservationTime, id);

        return ResponseEntity.ok(reservationTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> readReservationTime() {
        RowMapper<ReservationTime> rowMapper = (rs, rowNum) -> {
            ReservationTime reservationTime = new ReservationTime(
                    rs.getLong("id"),
                    rs.getString("start_at")
            );
            return reservationTime;
        };

        String sql = "select * from reservation_time";
        List<ReservationTime> reservations = jdbcTemplate.query(sql, rowMapper);

        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        String sql = "delete from reservation_time where id = ?";

        jdbcTemplate.update(sql, id);

        return ResponseEntity.ok().build();
    }
}
