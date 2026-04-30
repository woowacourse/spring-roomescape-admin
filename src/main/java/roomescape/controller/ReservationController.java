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
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;

@RestController
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations() {
        String sql = """
                 SELECT 
                     r.id as reservation_id, 
                     r.name, 
                     r.date, 
                     rt.id as time_id, 
                     rt.start_at as time_value  
                 FROM reservation r
                 inner join reservation_time rt
                 ON r.time_id = rt.id;
                """;

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            long id = resultSet.getLong("reservation_id");
            String name = resultSet.getString("name");
            String date = resultSet.getString("date");

            long timeId = resultSet.getLong("time_id");
            String timeValue = resultSet.getString("time_value");
            ReservationTime time = new ReservationTime(timeId, timeValue);
            return new Reservation(id, name, date, time);
        });
    }

    @PostMapping("/reservations")
    public Reservation postReservations(@RequestBody ReservationRequest reservationRequest) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES(?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});

            preparedStatement.setString(1, reservationRequest.getName());
            preparedStatement.setString(2, reservationRequest.getDate());
            preparedStatement.setLong(3, reservationRequest.getTimeId());

            return preparedStatement;
        }, keyHolder);
        long newId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        String findTimeSql = "select id, start_at from reservation_time where id = ?";
        ReservationTime reservationTime = jdbcTemplate.queryForObject(findTimeSql, (resultSet, rowNum)->{
            long id = resultSet.getLong("id");
            String startAt = resultSet.getString("start_at");
            return new ReservationTime(id, startAt);
        }, reservationRequest.getTimeId());

        return new Reservation(newId, reservationRequest.getName(), reservationRequest.getDate(), reservationTime);
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
