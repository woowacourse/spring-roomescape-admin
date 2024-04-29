package roomescape.Time.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.Time.domain.Time;

@Component
public class TimeJdbcDao implements TimeDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeJdbcDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Time save(Time time) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(time);
        long id = jdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
        time.setId(id);
        return time;
    }

    @Override
    public List<Time> findAll() {
        String findAllReservationTimeSql = "SELECT id, start_at FROM reservation_time ORDER BY start_at ASC ";

        return jdbcTemplate.query(findAllReservationTimeSql, (resultSet, rowNum) -> new Time(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        ));
    }

    @Override
    public Time findById(long reservationTimeId) {
        String findReservationTimeSql = "SELECT start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(findReservationTimeSql, (resultSet, rowNum) -> new Time(
                reservationTimeId,
                resultSet.getTime("start_at").toLocalTime()), reservationTimeId);
    }

    @Override
    public void deleteById(long reservationTimeId) {
        String deleteReservationTimeSql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(deleteReservationTimeSql, reservationTimeId);
    }
}
