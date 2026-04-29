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

@RestController
public class ReservationController {

    private final JdbcTemplate jdbcTemplate;

    public ReservationController(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/reservations")
    public List<Reservation> getReservations(){
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, (resultSet, rowNum)-> {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String date = resultSet.getString("date");
            String time = resultSet.getString("time");

            return new Reservation(id, name, date, time);
        });
    }

    @PostMapping("/reservations")
    public Reservation postReservations(@RequestBody Reservation reservation){
        String sql = "INSERT INTO reservation (name, date, time) VALUES(?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});

            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, reservation.getDate());
            preparedStatement.setString(3, reservation.getTime());

            return preparedStatement;
        }, keyHolder);
        long newId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new Reservation(newId, reservation.getName(), reservation.getDate(), reservation.getTime());
    }

    @DeleteMapping("/reservations/{id}")
    public void deleteReservation(@PathVariable Long id){
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
