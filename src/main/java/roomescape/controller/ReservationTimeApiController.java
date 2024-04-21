package roomescape.controller;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.ReservationTimeRequest;
import roomescape.entity.ReservationTime;

@RestController
@RequestMapping("/times")
public class ReservationTimeApiController {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public ReservationTimeApiController(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @GetMapping
    public List<ReservationTime> findAllReservationTimes() {
        String sql = "select * from reservation_time";

        return jdbcTemplate.query(sql, reservationTimeRowMapper());
    }

    @PostMapping
    public ReservationTime addReservationTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservationTimeRequest);
        long newId = insertActor.executeAndReturnKey(parameters).longValue();

        return reservationTimeRequest.toEntity(newId);
    }

    @DeleteMapping("/{id}")
    public void deleteReservationTime(@PathVariable long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<ReservationTime> reservationTimeRowMapper() {
        return (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );
    }
}
