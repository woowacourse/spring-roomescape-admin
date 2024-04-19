package roomescape.controller;

import java.net.URI;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import roomescape.ReservationDto;

@Controller
public class ReservationController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> reservations() {
        String sql = "SELECT id, name, date, time FROM reservation";
        RowMapper<ReservationDto> rowMapper = (resultSet, rowNum) -> new ReservationDto(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
        return ResponseEntity.ok(jdbcTemplate.query(sql, rowMapper));
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationDto> addReservation(@RequestBody ReservationDto reservationDto) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationDto.getName());
            ps.setDate(2, Date.valueOf(reservationDto.getDate()));
            ps.setTime(3, Time.valueOf(reservationDto.getTime()));
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        return ResponseEntity
                .created(URI.create("/reservations/" + id))
                .body(new ReservationDto(id, reservationDto));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.noContent().build();
    }
}
