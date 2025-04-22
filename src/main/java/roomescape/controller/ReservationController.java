package roomescape.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
    private SimpleJdbcInsert insertActor;

    public ReservationController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
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
        Number number = saveAndGetId(reservation);

        return ResponseEntity.ok().body(ReservationResponse.from(number.longValue(), reservation));
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

    private Number saveAndGetId(final Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time", reservation.getTime());
        return getGenerateId(parameters);
    }

    private Number getGenerateId(final Map<String, Object> parameters) {
        return insertActor.executeAndReturnKey(parameters);
    }
}
