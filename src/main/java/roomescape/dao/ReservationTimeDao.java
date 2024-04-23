package roomescape.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@Repository
public class ReservationTimeDao {

    private JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ReservationTime(rs.getLong("id"),
                        rs.getTime("start_at").toLocalTime()));
    }

    public ReservationTime findById(long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new ReservationTime(rs.getLong("id"), rs.getTime("start_at").toLocalTime()),
                id);
    }

    public long save(ReservationTimeRequest reservationTimeRequest) {
        String sql = "INSERT INTO reservation_time (start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, reservationTimeRequest.startAt(), keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
