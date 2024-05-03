package roomescape.dao.web;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationTimeRepository;
import roomescape.entity.ReservationTime;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReservationTimeJdbcRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeJdbcRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_times")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime createReservationTime(ReservationTime reservationTime) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("start_at", reservationTime.getStartAt());
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new ReservationTime(id, reservationTime.getStartAt());
    }

    @Override
    public ReservationTime findReservationTime(Long id) {
        String sql = "SELECT id, start_at FROM reservation_times WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getTime("start_at").toLocalTime()), id);
    }

    @Override
    public List<ReservationTime> findReservationTimes() {
        String sql = "SELECT id, start_at FROM reservation_times";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getTime("start_at").toLocalTime()));
    }

    @Override
    public void removeReservationTime(Long id) {
        jdbcTemplate.update("DELETE reservation_times WHERE id = ?", id);
    }
}
