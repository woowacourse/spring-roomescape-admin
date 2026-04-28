package roomescape.reservation.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.entity.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {
    private static final String SELECT_ALL = "SELECT * FROM reservation";

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public ReservationsController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> readAll(){
        List<Reservation> reservations = jdbcTemplate.query(SELECT_ALL, reservationRowMapper());

        return ResponseEntity.ok(reservations);
    }

    public RowMapper<Reservation> reservationRowMapper(){
        return (resultSet, rowNum) ->  new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                resultSet.getTime("time").toLocalTime()
        );
    }

    @PostMapping
    public ResponseEntity<Reservation> add(@RequestBody ReservationRequestDto reservationRequestDto){
        return null;
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
    }
}
