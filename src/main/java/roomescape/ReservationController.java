package roomescape;

import java.sql.PreparedStatement;
import java.util.List;
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

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final JdbcTemplate jdbcTemplate;
    private final TimeRepository timeRepository;

//    public ReservationController(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    public ReservationController(JdbcTemplate jdbcTemplate, TimeRepository timeRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.timeRepository = timeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> get() {
        String sql = "select r.id as reservation_id, r.name, r.date, rt.id as time_id, rt.start_at from reservation r inner join reservation_time rt on r.time_id = rt.id";
        List<Reservation> find = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Reservation(
                        resultSet.getLong("reservation_id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        new ReservationTime(resultSet.getLong("time_id"), resultSet.getString("start_at"))));
        return ResponseEntity.ok(find);
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationCreateDto reservationCreateDto) {

        ReservationTime find = timeRepository.findById(reservationCreateDto.getTimeId())
                .orElseThrow(() -> new IllegalArgumentException("조회된 타임 슬롯이 없습니다."));

        String sql = "insert into reservation(name, date, time_id) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement pstmt = connection.prepareStatement(
                    sql,
                    new String[]{"id"});

            pstmt.setString(1, reservationCreateDto.getName());
            pstmt.setString(2, reservationCreateDto.getDate());
            pstmt.setLong(3, find.getId());
            return pstmt;
        }, keyHolder);

        Reservation reservation = jdbcTemplate.queryForObject(
                "select id, name, date, time_id from reservation where id = ?",
                (resultSet, rowNum) -> {
                    return new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            new ReservationTime(find.getId(), find.getStartAt()));
                },
                keyHolder.getKey().longValue());
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
        return ResponseEntity.ok().build();
    }
}
