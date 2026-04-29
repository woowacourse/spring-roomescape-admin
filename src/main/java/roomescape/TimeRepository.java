package roomescape;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime create(String time) {
        String sql = "insert into reservation_time(start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement pstmt = connection.prepareStatement(sql, new String[]{"id"});
                    pstmt.setString(1, time);
                    return pstmt;
                },
                keyHolder);

        return jdbcTemplate.queryForObject("select id, start_at from reservation_time where id = ?",
                (resultSet, rowNum) -> {
                    return new ReservationTime(resultSet.getLong("id"), resultSet.getString("start_at"));
                },
                keyHolder.getKey().longValue());
    }

    public List<ReservationTime> findAll() {
        String sql = "select id, start_at from reservation_time";

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    return new ReservationTime(resultSet.getLong("id"), resultSet.getString("start_at"));
                }
        );
    }

    public void delete(long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
