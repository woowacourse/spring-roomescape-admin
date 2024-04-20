package roomescape.controller;

import java.net.URI;
import java.util.List;
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
import roomescape.controller.request.CreateReservationRequest;
import roomescape.controller.response.ReservationResponse;
import roomescape.controller.response.ReservationTimeResponse;
import roomescape.domain.Name;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@RestController
@RequestMapping("/reservations")
class ReservationController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleInsert;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> reserve(@RequestBody CreateReservationRequest request) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(request);
        Number key = simpleInsert.executeAndReturnKey(parameterSource);

        // create new Reservation
        Reservation newReservation = new Reservation(
                key.longValue(),
                new Name(request.name()),
                request.date(),
                resolveTime(request.timeId())
        );

        // build response dto
        ReservationResponse response = toDto(newReservation);
        return ResponseEntity.created(URI.create("/reservations/" + key))
                .body(response);
    }

    private ReservationTime resolveTime(Long timeId) {
        String sql = "select * from reservation_time where id = ?";
        ReservationTime reservationTime = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getTime("start_at").toLocalTime()
        ), timeId);

        return reservationTime;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBy(@PathVariable Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations() {
        String sql = """
                    select 
                        r.id as reservation_id,
                        r.name as reservation_name,
                        r.date as reservation_date,
                        t.id as time_id,
                        t.start_at as time_value
                    from reservation as r
                    inner join reservation_time as t
                    on r.time_id = t.id
                """;

        List<Reservation> reservations = jdbcTemplate.query(sql, (rs, rowNum) ->
                new Reservation(
                        rs.getLong("reservation_id"),
                        new Name(rs.getString("reservation_name")),
                        rs.getDate("reservation_date").toLocalDate(),
                        new ReservationTime(
                                rs.getLong("time_id"),
                                rs.getTime("time_value").toLocalTime()
                        )
                )
        );

        return ResponseEntity.ok(reservations.stream()
                .map(this::toDto)
                .toList());
    }

    private ReservationResponse toDto(Reservation newReservation) {
        ReservationTime time = newReservation.getTime();
        ReservationTimeResponse timeResponse = new ReservationTimeResponse(time.getId(), time.getStartAt());
        return new ReservationResponse(
                newReservation.getId(),
                newReservation.getName(),
                newReservation.getDate(),
                timeResponse
        );
    }
}
