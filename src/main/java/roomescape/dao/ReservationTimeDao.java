package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTime;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_times")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> findReservationTimes() {
        String sql = "SELECT id, start_at FROM reservation_times";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getTime("start_at").toLocalTime()));
    }

    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationTime);
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return new ReservationTime(id, reservationTime.getStartAt());
    }

    public void removeReservationTime(Long id) {
        jdbcTemplate.update("DELETE reservation_times WHERE id = ?", id);
    }
}
