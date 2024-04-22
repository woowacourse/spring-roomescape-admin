package roomescape.repository;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source)
            .withTableName("reservation_time")
            .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> findAll() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(
            sql,
            (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
            )
        );
    }

    public ReservationTime save(ReservationTime reservationTime) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationTime);
        Long id = jdbcInsert.executeAndReturnKey(params)
            .longValue();
        return new ReservationTime(id, reservationTime.getStartAt());
    }
}
