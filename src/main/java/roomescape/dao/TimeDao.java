package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TimeDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Long save(Time request) {
        String query = "INSERT into reservation_time(start_at) VALUES(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    query,
                    new String[]{"id"});
            ps.setString(1, request.getStartAt());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Time> readAll() {
        String query = "SELECT * FROM reservation_time";
        List<Time> times = jdbcTemplate.query(query,
                (rs, rowNum) -> {
                    Time time = new Time(
                            rs.getLong("id"),
                            rs.getString("start_at"));
                    return time;
                }
        );
        return times;
    }

    public void delete(Long id) {
        String query = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(query, id);
    }
}
