package roomescape.controller;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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

    @PostMapping
    public ReservationTime addReservationTime(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(reservationTimeRequest);
        long newId = insertActor.executeAndReturnKey(parameters).longValue();

        return reservationTimeRequest.toEntity(newId);
    }
}
