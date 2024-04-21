package roomescape.controller;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationRequest;
import roomescape.entity.Reservation;
import roomescape.entity.ReservationTime;

@RestController
@RequestMapping("/reservations")
public class ReservationApiController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;
    private final ReservationTimeApiController timeApiController;

    public ReservationApiController(JdbcTemplate jdbcTemplate, DataSource dataSource,
                                    ReservationTimeApiController timeApiController) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        this.timeApiController = timeApiController;
    }

    @GetMapping
    public List<Reservation> findAllReservations() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value"
                + " FROM reservation as r"
                + " INNER JOIN reservation_time as t"
                + " ON r.time_id = t.id";

        return jdbcTemplate.query(sql, reservationRowMapper());
    }

    @PostMapping
    public Reservation addReservation(@RequestBody ReservationRequest reservationRequest) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", reservationRequest.name())
                .addValue("date", reservationRequest.date())
                .addValue("time_id", reservationRequest.timeId());

        long newId = insertActor.executeAndReturnKey(parameters).longValue();

        ReservationTime reservationTime = timeApiController.findByTimeId(reservationRequest.timeId());

        return reservationRequest.toEntity(newId, reservationTime);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (resultSet, rowNum) -> new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                timeApiController.findByTimeId(resultSet.getLong("time_id"))
        );
    }
}
