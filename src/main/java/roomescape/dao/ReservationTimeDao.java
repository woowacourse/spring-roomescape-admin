package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime findById(Long id) {
        String sql = "SELECT start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new ReservationTime(
                        id,
                        rs.getString("start_at")
                ), id
        );
    }
}
