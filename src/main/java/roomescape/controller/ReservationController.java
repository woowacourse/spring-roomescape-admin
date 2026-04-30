package roomescape.controller;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationRequest;
import roomescape.domain.ReservationTime;

@Controller
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        Reservation reservation = new Reservation(
                resultSet.getLong("reservation_id"),
                Name.parse(resultSet.getString("name")),
                LocalDate.parse(resultSet.getString("date")),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        ReservationTime.parse(resultSet.getString("time_value"))
                )
        );
        return reservation;
    };

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("id"),
                ReservationTime.parse(resultSet.getString("start_at"))
        );
        return reservationTime;
    };

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> read() {
        String sql = """
                select 
                    r.id as reservation_id,
                    r.name as name,
                    r.date as date,
                    t.id as time_id,
                    t.start_at as time_value
                from reservations as r
                inner join reservation_times as t
                    on r.time_id = t.id
                """;

        List<Reservation> reservations = jdbcTemplate.query(sql, reservationRowMapper);

        return ResponseEntity.ok().body(reservations);
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody ReservationRequest request) {
        String createSql = "insert into reservations (name, date, time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(createSql, new String[]{"id"});
            ps.setString(1, request.getName());
            ps.setString(2, request.getDate());
            ps.setLong(3, request.getTimeId());
            return ps;
        }, keyHolder);

        Long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        String readSql = "select id, start_at from reservation_times where id = ?";
        ReservationTime time = jdbcTemplate.queryForObject(readSql, reservationTimeRowMapper, request.getTimeId());

        Reservation newReservation = new Reservation(
                generatedId,
                Name.parse(request.getName()),
                LocalDate.parse(request.getDate()),
                time
        );
        return ResponseEntity.ok().body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        String sql = "delete from reservations where id = ?";

        jdbcTemplate.update(sql, id);

        return ResponseEntity.ok().build();
    }
}
