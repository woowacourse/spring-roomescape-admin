package roomescape;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AdminController {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            LocalDate.parse(resultSet.getString("date")),
            new ReservationTime(resultSet.getString("time")));

    public AdminController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            sql,
                            new String[]{"id"}
                    );
                    preparedStatement.setString(1, reservation.getName());
                    preparedStatement.setString(2, reservation.getDate().toString());
                    preparedStatement.setString(3, reservation.getTime().toString());
                    return preparedStatement;
                }, keyHolder
        );

        long id = keyHolder.getKey().longValue();
        Reservation newReservation = Reservation.toEntity(reservation, id);
        return ResponseEntity.ok(newReservation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> readAll() {
        String sql = "SELECT id, name, date, time FROM reservation";
        List<Reservation> reservations = jdbcTemplate.query(
                sql,
                reservationRowMapper
        );

        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<Reservation> read(@PathVariable Long id) {
        String sql = "SELECT id, name, date, time FROM reservation WHERE id = ?";
        Reservation reservation = jdbcTemplate.queryForObject(
                sql,
                reservationRowMapper,
                id
        );

        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        jdbcTemplate.update(
                sql,
                id
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> createTime(@RequestBody ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time(start_at) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            sql,
                            new String[]{"id"}
                    );
                    preparedStatement.setString(1, reservationTime.getStartAt());
                    return preparedStatement;
                }, keyHolder
        );

        long id = keyHolder.getKey().longValue();
        ReservationTime newReservationTime = ReservationTime.toEntity(reservationTime, id);

        return ResponseEntity.ok(newReservationTime);
    }

    @GetMapping("/times")
    public ResponseEntity<List<ReservationTime>> readTimeAll() {
        String sql = "SELECT start_at FROM reservation_time";

        List<ReservationTime> reservationTimes = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getString("start_at")
                ));

        return ResponseEntity.ok(reservationTimes);
    }

    @GetMapping("/times/{id}")
    public ResponseEntity<ReservationTime> readTime(@PathVariable Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";

        ReservationTime reservationTime = jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                ),
                id);

        return ResponseEntity.ok(reservationTime);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";

        jdbcTemplate.update(
                sql,
                id
        );

        return ResponseEntity.ok().build();
    }
}
