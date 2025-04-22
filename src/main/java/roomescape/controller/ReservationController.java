package roomescape.controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
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
import roomescape.Reservation;
import roomescape.controller.request.ReservationRequest;
import roomescape.controller.response.ReservationResponse;

@RequestMapping("/reservations")
@RestController
public final class ReservationController {

    private final JdbcTemplate jdbcTemplate;

    public ReservationController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    ResponseEntity<List<ReservationResponse>> read() {
        final String sql = "select id, name, date, time from reservation";
        final RowMapper<Reservation> rowMapper = getRowMapper();
        final List<Reservation> reservations = jdbcTemplate.query(sql, rowMapper);

        final List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::of)
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.toReservation();
        final long id = saveAndGetId(reservation);
        return ResponseEntity.ok().body(ReservationResponse.from(id, reservation));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        final String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.ok().build();
    }

    private RowMapper<Reservation> getRowMapper() {
        return (resultSet, rowNum) ->
                Reservation.from(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getTime("time").toLocalTime());
    }

    private long saveAndGetId(final Reservation reservation) {
        String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setDate(2, Date.valueOf(reservation.getDate()));
            ps.setTime(3, Time.valueOf(reservation.getTime()));
            return ps;
        }, keyHolder);

        return getGenerateId(keyHolder);
    }

    private long getGenerateId(final KeyHolder keyHolder) {
        return keyHolder.getKey().longValue();
    }
}
