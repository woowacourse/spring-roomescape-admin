package roomescape.controller;

import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationAddRequest;
import roomescape.dto.ReservationTimeAddRequest;

@RestController
public class ReservationController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/reservations")
    public List<Reservation> getReservationList() {
        return jdbcTemplate.query("select * from reservation",
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getTime("time").toLocalTime()
                    );
                    return reservation;
                });
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationAddRequest reservationAddRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation (name, date, time) values (?,?,?)",
                    new String[]{"id"}
            );
            ps.setString(1, reservationAddRequest.getName());
            ps.setString(2, reservationAddRequest.getDate().toString());
            ps.setString(3, reservationAddRequest.getTime().toString());
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();

        String sql = "select * from reservation where id = ?";
        Reservation reservation = jdbcTemplate.queryForObject(sql, new RowMapper<Reservation>() {
            @Override
            public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Reservation(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getTime("time").toLocalTime()
                );
            }
        }, id);
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> removeReservation(@PathVariable("id") Long id) {
        if (jdbcTemplate.update("delete from reservation where id = ?", id) > 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/times")
    public ResponseEntity<ReservationTime> addReservationTime(
            @RequestBody ReservationTimeAddRequest reservationTimeAddRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation_time (start_at) values (?)",
                    new String[]{"id"}
            );
            ps.setString(1, reservationTimeAddRequest.getStartAt().toString());
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();
        String sql = "select * from reservation_time where id = ?";
        ReservationTime reservationTime = jdbcTemplate.queryForObject(sql, new RowMapper<ReservationTime>() {
            @Override
            public ReservationTime mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ReservationTime(
                        rs.getLong("id"),
                        rs.getTime("start_at").toLocalTime()
                );
            }
        }, id);
        return ResponseEntity.ok(reservationTime);
    }
}
