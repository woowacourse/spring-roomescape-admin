package roomescape.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDAO {

    private JdbcTemplate jdbcTemplate;

    public ReservationTimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime insert(ReservationTime reservationTime) {
        jdbcTemplate.update("insert into reservation_time (start_at) values (?)", reservationTime.getStartAt());

        Long id = jdbcTemplate.queryForObject("select t.id from reservation_time t where t.start_at = ?", Long.class,
                reservationTime.getStartAt());
        return ReservationTime.of(id, reservationTime.getStartAt());
    }

    public List<ReservationTime> findAll() {
        String sql = "select id, start_at from reservation_time";
        RowMapper<ReservationTime> rowMapper = (resultSet, rowNum) -> ReservationTime.of(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        );

        return jdbcTemplate.query(sql, rowMapper);
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }
}
