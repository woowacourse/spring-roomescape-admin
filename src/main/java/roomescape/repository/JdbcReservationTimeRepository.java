package roomescape.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.domain.ReservationTime;

public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("reservation_time")
                .usingColumns("start_at")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new ReservationTime(rs.getLong("id"), rs.getTime("start_at").toLocalTime())
        );
    }

    @Override
    public ReservationTime add(ReservationTime reservationTime) {
        SqlParameterSource parameter = new BeanPropertySqlParameterSource(reservationTime);
        Long newId = simpleJdbcInsert.executeAndReturnKey(parameter).longValue();
        return new ReservationTime(newId, reservationTime);
    }
}
