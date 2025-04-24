package roomescape.controller.reservation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
import roomescape.controller.reservation.request.ReservationRequest;
import roomescape.controller.reservation.response.ReservationResponse;
import roomescape.controller.reservationtime.ReservationTimeController;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@RequestMapping("/reservations")
@RestController
public final class ReservationController {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertActor;

    @Autowired
    private ReservationTimeController reservationTimeController;

    public ReservationController(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> read() {
        final String sql = "SELECT \n"
                + "    r.id as reservation_id, \n"
                + "    r.name, \n"
                + "    r.date, \n"
                + "    t.id as time_id, \n"
                + "    t.start_at as time_value \n"
                + "FROM reservation as r \n"
                + "inner join reservation_time as t \n"
                + "on r.time_id = t.id";
        final RowMapper<Reservation> rowMapper = getRowMapper();
        final List<Reservation> reservations = jdbcTemplate.query(sql, rowMapper);

        final List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::of)
                .toList();

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest) {
        ReservationTime time = reservationTimeController.findById(reservationRequest.timeId());
        Reservation reservation = reservationRequest.toReservation(time);
        long id = saveAndGetId(reservation).longValue();

        return ResponseEntity.ok().body(ReservationResponse.from(id, reservation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        final String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.ok().build();
    }

    private RowMapper<Reservation> getRowMapper() {
        return (resultSet, rowNum) ->
        {
            long id = resultSet.getLong("time_id");
            ReservationTime time = reservationTimeController.findById(id);

            return Reservation.from(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getDate("date").toLocalDate(),
                    time);
        };
    }

    private Number saveAndGetId(final Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>(3);
        parameters.put("name", reservation.getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time_id", reservation.getTime().getId());
        return getGenerateId(parameters);
    }

    private Number getGenerateId(final Map<String, Object> parameters) {
        return insertActor.executeAndReturnKey(parameters);
    }
}
