package roomescape.controller;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequestDto;
import roomescape.entity.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    private final JdbcTemplate jdbcTemplate;

    public ReservationApiController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<Reservation> findAllReservations() {
        String sql = "SELECT * FROM reservation";

        return jdbcTemplate.query(sql, reservationRowMapper());
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
        String sql = "insert into reservation(name, date, time) values(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationRequestDto.getName());
            ps.setString(2, reservationRequestDto.getDate());
            ps.setString(3, reservationRequestDto.getTime());

            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        Reservation reservation = reservationRequestDto.toEntity(id);

        return ResponseEntity.created(URI.create("/reservations/" + id)).body(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.noContent().build();
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
    }
}
