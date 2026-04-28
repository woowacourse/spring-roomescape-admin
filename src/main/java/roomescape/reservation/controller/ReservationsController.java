package roomescape.reservation.controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import javax.swing.plaf.basic.BasicTreeUI.KeyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    private static final String INSERT = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?);";
    private static final String DELETE = "DELETE FROM reservation WHERE id = ?";

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
    public ResponseEntity<Reservation> add(@RequestBody ReservationRequestDto reservationRequestDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT, new String[]{"id"});
            ps.setString(1, reservationRequestDto.name());
            ps.setDate(2, Date.valueOf(reservationRequestDto.date()));
            ps.setTime(3, Time.valueOf(reservationRequestDto.time()));
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        Reservation reservation = new Reservation(
                id,
                reservationRequestDto.name(),
                reservationRequestDto.date(),
                reservationRequestDto.time()
        );

        return ResponseEntity.ok(reservation);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        jdbcTemplate.update(DELETE, id);
    }
}
