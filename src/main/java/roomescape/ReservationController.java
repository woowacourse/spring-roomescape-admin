package roomescape;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
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

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    @GetMapping
    public ResponseEntity<List<Reservation>> get() {
        String sql = "select id, name, date, time from reservation";
        List<Reservation> find = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    return new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time"));
                }
        );
        return ResponseEntity.ok(find);
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody ReservationCreateDto reservationCreateDto) {
        String name = reservationCreateDto.getName();
        String date = reservationCreateDto.getDate();
        String time = reservationCreateDto.getTime();

        reservations.add(new Reservation(index.incrementAndGet(), name, date, time));

        Reservation create = reservations.stream()
                .filter(reservation -> reservation.getId() == index.get())
                .findAny()
                .orElseThrow(() ->
                        new IllegalArgumentException("요청한 번호를 찾을 수 없습니다."));

        return ResponseEntity.ok(create);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Reservation delete = reservations.stream()
                .filter(reservation -> reservation.getId() == id)
                .findAny()
                .orElseThrow(() ->
                        new IllegalArgumentException("요청한 번호를 찾을 수 없습니다."));
        reservations.remove(delete);
        return ResponseEntity.ok().build();
    }
}
