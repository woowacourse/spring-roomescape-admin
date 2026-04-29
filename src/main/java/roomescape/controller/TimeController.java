package roomescape.controller;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.ReservationTime;

@RestController
public class TimeController {

    private final JdbcTemplate jdbcTemplate;

    public TimeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @GetMapping("/times")
    public List<ReservationTime> getReservationTime(){
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum)-> {
            long id = resultSet.getLong("id");
            String startAt = resultSet.getString("start_at");

            return new ReservationTime(id,startAt);
        });
   }

    @PostMapping("/times")
    public ReservationTime postReservationTime(@RequestBody ReservationTime reservationTime){
        String sql = "INSERT INTO reservation_time (start_at) VALUES(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection-> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1,reservationTime.getStartAt());

            return preparedStatement;
        }, keyHolder);
        long newId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new ReservationTime(newId, reservationTime.getStartAt());
    }

    @DeleteMapping("/times/{id}")
    public void deleteReservationTime(@PathVariable Long id){
        String sql = "DELETE FROM reservation_time WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
