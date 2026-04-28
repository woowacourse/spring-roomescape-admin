package roomescape.reservation.controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
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
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.time.entity.Time;

@RestController
@RequestMapping("/reservations")
public class ReservationsController {

    private static final String SELECT_ALL =
            "SELECT " +
                    "r.id, " +
                    "r.name, " +
                    "r.date, " +
                    "t.id as time_id, " +
                    "t.start_at " +
                    "FROM reservation r " +
                    "INNER JOIN reservation_time t " +
                    "ON r.time_id = t.id";
    private static final String SELECT_TIME = "SELECT * FROM reservation_time WHERE id = ?";
    private static final String INSERT_RESERVATION = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
    private static final String DELETE = "DELETE FROM reservation WHERE id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ReservationsController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> readAll(){
        List<ReservationResponseDto> reservations = jdbcTemplate.query(SELECT_ALL, reservationRowMapper());

        return ResponseEntity.ok(reservations);
    }

    public RowMapper<ReservationResponseDto> reservationRowMapper(){
        return (resultSet, rowNum) ->  new ReservationResponseDto(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                new Time(
                        resultSet.getLong("time_id"),
                        resultSet.getString("start_at")
                )
        );
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> add(@RequestBody ReservationRequestDto reservationRequestDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_RESERVATION, new String[]{"id"});
            ps.setString(1, reservationRequestDto.name());
            ps.setString(2, reservationRequestDto.date());
            ps.setLong(3,reservationRequestDto.timeId());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        Time time = jdbcTemplate.queryForObject(
                SELECT_TIME,
                (rs, rowNum) -> new Time(
                        rs.getLong("id"),
                        rs.getString("start_at")
                ),
                reservationRequestDto.timeId()
        );

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto(
                id,
                reservationRequestDto.name(),
                reservationRequestDto.date(),
                time
        );

        return ResponseEntity.ok(reservationResponseDto);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        jdbcTemplate.update(DELETE, id);
    }
}
