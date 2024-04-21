package roomescape.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.CreateReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(
                Objects.requireNonNull(jdbcTemplate.getDataSource()))
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(@RequestBody CreateReservationRequest request) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(request);
        Number key = simpleJdbcInsert.executeAndReturnKey(parameterSource);

        return ResponseEntity.created(URI.create("/reservations/" + key))
                .body(new ReservationResponse(
                        key.longValue(),
                        request.name(),
                        request.date(),
                        request.time()
                ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBy(@PathVariable Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        String sql = "SELECT * FROM reservation";
        List<ReservationResponse> reservationResponses = jdbcTemplate.query(
                sql,
                ((rs, rowNum) ->
                        new ReservationResponse(
                                rs.getLong("id"),
                                rs.getString("name"),
                                LocalDate.parse(rs.getString("date")),
                                LocalTime.parse(rs.getString("time"))
                        ))
        );

        return ResponseEntity.ok(reservationResponses);
    }
}
