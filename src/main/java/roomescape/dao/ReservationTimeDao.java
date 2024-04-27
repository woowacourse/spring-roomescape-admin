package roomescape.dao;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.domain.ReservationTime;
import roomescape.dto.reservationtime.ReservationTimeRequest;

@Component
public class ReservationTimeDao {

    private static final RowMapper<ReservationTime> RESERVATION_TIME_ROW_MAPPER = (resultSet, rowNum) ->
            new ReservationTime(
                    resultSet.getLong("id"),
                    resultSet.getTime("start_at").toLocalTime()
            );

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> findAllReservationTimes() {
        String sql = "select id, start_at from reservation_time";

        return jdbcTemplate.query(sql, RESERVATION_TIME_ROW_MAPPER);
    }

    public Long saveReservationTime(ReservationTimeRequest reservationTimeRequest) {
        return simpleJdbcInsert.executeAndReturnKey(
                        Map.of(
                                "start_at", reservationTimeRequest.startAt()
                        ))
                .longValue();
    }

    public void deleteReservationTime(Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }
}
