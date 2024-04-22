package roomescape.controller;

import java.net.URI;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Reservation> actorRowMapper = (rs, rowNum) ->
            new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time").toLocalTime()
            );

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponse> findAll() {
        String sql = "SELECT * FROM reservation";
        List<Reservation> reservations = jdbcTemplate.query(sql, actorRowMapper);
        return ReservationResponse.fromReservations(reservations);
    }

    private Reservation findById(final long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        Reservation reservation = jdbcTemplate.queryForObject(sql, actorRowMapper, id);
        return reservation;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> add(
            @RequestBody final ReservationRequest reservationRequest) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationRequest.name());
            ps.setDate(2, Date.valueOf(reservationRequest.date()));
            ps.setLong(3, reservationRequest.timeId());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        Reservation reservation = findById(id);
        ReservationResponse reservationResponse = ReservationResponse.from(reservation);
        return ResponseEntity.created(URI.create("/reservations/" + id)).body(reservationResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int delete(@PathVariable(name = "id") final long id) {
        String sql = "DELETE FROM reservation WHERE id = ?"; // TODO: 만약 찾을 수 없는 아이디면?
        return jdbcTemplate.update(sql, id);
    }
}
