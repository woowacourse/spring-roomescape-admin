package roomescape.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class RoomescapeController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertExecutor;

    public RoomescapeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertExecutor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) ->
            Reservation.create(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getObject("date", LocalDate.class),
                    rs.getObject("time", LocalTime.class)
    );

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";

        List<Reservation> reservations = jdbcTemplate.query(sql, reservationRowMapper);

        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(request);

        Number newId = insertExecutor.executeAndReturnKey(params);

        Reservation reservation = Reservation.create(
                newId.longValue(),
                request.name(),
                request.date(),
                request.time()
        );

        return ResponseEntity.ok(ReservationResponse.from(reservation));
    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteReservation(@PathVariable String id) {
//        reservations.removeIf(reservation -> reservation.id() == Long.parseLong(id));
//
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
}
