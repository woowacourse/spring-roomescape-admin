package roomescape.dao;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimes;

@Component
public class ReservationTimeDao {

    private static final String FIND_ALL_SQL = "select * from reservation_time";
    private static final String DELETE_BY_ID_SQL = "delete from reservation_time where id = ?";
    private static final RowMapper<ReservationTime> RESERVATION_ROW_MAPPER = (resultSet, row) ->
            new ReservationTime(
                    resultSet.getLong("id"),
                    resultSet.getObject("start_at", LocalTime.class)
            );

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTimes findAll() {
        return new ReservationTimes(jdbcTemplate.query(
                FIND_ALL_SQL,
                RESERVATION_ROW_MAPPER
        ));
    }

    public ReservationTime save(ReservationTime reservationTime) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>(2);
        params.put("start_at", reservationTime.getTime());

        Long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return new ReservationTime(id, reservationTime);
    }

    public boolean deleteById(Long id) {
        int updatedRows = jdbcTemplate.update(DELETE_BY_ID_SQL, id);
        return updatedRows > 0;
    }
}
