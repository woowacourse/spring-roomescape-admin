package roomescape;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime insert(ReservationTime reservationTime) {
        final String sql = "insert into reservation_time (start_at) values(?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);
        return ReservationTime.withId(keyHolder.getKey().longValue(), reservationTime);
    }

    public List<ReservationTime> select() {
        final String sql = "select id, start_at from reservation_time;";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                ));
    }

    public void delete(long id) {
        final String sql = "delete from reservation_time where id = ?;";
        jdbcTemplate.update(sql, id);
    }

    public ReservationTime selectById(long id) {
        final String sql = "select * from reservation_time where id = ?;";
        return jdbcTemplate.queryForObject(sql,
                (resultSet,rowNum) -> new ReservationTime(
                    resultSet.getLong("id"),
                    resultSet.getString("start_at")
                ), id);
    }
}
