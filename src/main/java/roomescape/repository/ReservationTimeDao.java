package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public void save(ReservationTime reservationTime) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(reservationTime);
        Number key = jdbcInsert.executeAndReturnKey(parameterSource);

        reservationTime.setId(key.longValue());
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))));
    }

    public ReservationTime findById(Long id) {
        String sql = """
                SELECT * FROM reservation_time
                WHERE id = ?""";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))), id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
