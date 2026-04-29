package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.ReservationCreateRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.time.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getReservations() {
        String sql = "select r.id as reservation_id, r.name, r.date, rt.id as time_id, rt.start_at " +
                "from reservation r " +
                "join reservation_time rt on r.time_id = rt.id";
        List<ReservationResponseDto> reservations =
                jdbcTemplate.query(sql,
                        (resultSet, rowNum) -> ReservationResponseDto.from(Reservation.of(
                                resultSet.getLong("reservation_id"),
                                resultSet.getString("name"),
                                resultSet.getString("date"),
                                new ReservationTime(
                                        resultSet.getLong("time_id"),
                                        resultSet.getString("start_at")
                                )
                        ))
                );
        return ResponseEntity.ok(reservations);
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody ReservationCreateRequestDto request) {
        String insertSql = "insert into reservation(name, date, time_id) values (?, ?, ?)";
        String selectTimeSql = "select * from reservation_time where id = ?";

        ReservationTime time = jdbcTemplate.queryForObject(selectTimeSql,
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                ), request.timeId());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, new String[]{"id"});
            ps.setString(1, request.name());
            ps.setString(2, request.date());
            ps.setLong(3, request.timeId());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        ReservationResponseDto response = ReservationResponseDto.from(Reservation.of(id, request.name(), request.date(), new ReservationTime(time.getId(), time.getStartAt())));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        jdbcTemplate.update("delete from reservation where id = ?", id);
        return ResponseEntity.ok().build();
    }
}
