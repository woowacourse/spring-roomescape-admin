package roomescape.repository;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeRepository {

    private static final RowMapper<ReservationTime> TIME_ROW_MAPPER = (resultSet, rowNum) -> {
        return new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );
    };

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id")
                .usingColumns("start_at");
    }

    public ReservationTime insert(ReservationTime time) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(time);
        long id = jdbcInsert.executeAndReturnKey(params).intValue();

        return new ReservationTime(id, time.startAt());
    }

    public List<ReservationTime> list() {
        String sql = "SELECT * FROM reservation_time";

        return jdbcTemplate.query(sql, TIME_ROW_MAPPER);
    }

    public void delete(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public ReservationTime findById(long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, TIME_ROW_MAPPER, id);
    }
}
