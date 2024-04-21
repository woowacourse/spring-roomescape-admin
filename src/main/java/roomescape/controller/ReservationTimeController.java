package roomescape.controller;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.controller.dto.ReservationTimeCreateRequest;
import roomescape.controller.dto.ReservationTimeCreateResponse;
import roomescape.controller.dto.ReservationTimeFindResponse;
import roomescape.domain.ReservationTime;
import roomescape.util.CustomDateTimeFormatter;

@RestController
@RequestMapping("/times")
public class ReservationTimeController {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<ReservationTimeFindResponse> getReservationTimes() {
        List<ReservationTime> reservationTimes = jdbcTemplate.query(
                "SELECT * FROM reservation_time"
                , (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        CustomDateTimeFormatter.getLocalTime(rs.getString("start_at"))
                )
        );

        return reservationTimes.stream()
                .map(ReservationTimeFindResponse::of)
                .toList();
    }

    @PostMapping
    public ReservationTimeCreateResponse createReservationTime(
            @RequestBody ReservationTimeCreateRequest reservationTimeCreateRequest) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
        long createdReservationTimeId = simpleJdbcInsert.executeAndReturnKey(Map.of(
                "start_at", reservationTimeCreateRequest.startAt()
        )).longValue();

        ReservationTime createdReservationTime = jdbcTemplate.queryForObject(
                "SELECT * FROM reservation_time WHERE id = ?",
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        CustomDateTimeFormatter.getLocalTime(rs.getString("start_at")
                        )),
                createdReservationTimeId);

        return ReservationTimeCreateResponse.of(createdReservationTime);
    }

    @DeleteMapping("/{id}")
    public void deleteReservationTime(@PathVariable Long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }
}
