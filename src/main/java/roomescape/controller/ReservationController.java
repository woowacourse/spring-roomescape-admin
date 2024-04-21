package roomescape.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationCreateRequest;
import roomescape.controller.dto.ReservationCreateResponse;
import roomescape.controller.dto.ReservationFindResponse;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.util.CustomDateTimeFormatter;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String findQuery = """
            SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
            FROM reservation as r
            inner join reservation_time as t
            on r.time_id = t.id""";
    private final RowMapper<Reservation> reservationRowMapper = (rs, rowNum) ->
            new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    CustomDateTimeFormatter.getLocalDate(rs.getString("date")),
                    new ReservationTime(
                            rs.getLong("time_id"),
                            CustomDateTimeFormatter.getLocalTime(rs.getString("time_value"))
                    )
            );

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ReservationFindResponse> getReservation() {

        List<Reservation> reservations = jdbcTemplate.query(findQuery, reservationRowMapper);
        return reservations.stream()
                .map(ReservationFindResponse::of)
                .toList();
    }

    @PostMapping
    //@ResponseStatus(value = HttpStatus.CREATED)
    //TODO : 헤더 조작을 위해 ResponseEntity를 반환할 필요성이 생겼다. 다른 메서드도 동일하게 변경해 주어야 할까?
    public ResponseEntity<ReservationCreateResponse> createReservation(
            @RequestBody ReservationCreateRequest reservationCreateRequest) {

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        Long createdReservationId = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "name", reservationCreateRequest.name(),
                "date", reservationCreateRequest.date(),
                "time_id", reservationCreateRequest.timeId()
        )).longValue();

        //TODO : 이렇게 조회를 하는 것이 좋을지, Request의 데이터를 사용하는 것이 좋을지 고민해보기
        Reservation createdReservation = jdbcTemplate.queryForObject(findQuery,
                reservationRowMapper);

        return ResponseEntity.status(HttpStatus.OK)
                //.header("Location", "/reservations/" + createdReservationId)
                .body(ReservationCreateResponse.of(createdReservation));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
