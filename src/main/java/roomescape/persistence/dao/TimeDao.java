package roomescape.persistence.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

@Repository
public class TimeDao {
    private final JdbcTemplate jdbcTemplate;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(Time time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = """
                INSERT INTO reservation_time
                (start_at)
                VALUES (?)
                """;
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, time.getStartAt().toString());
            return pstmt;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Time findById(Long id) {
        String sql = """
                SELECT * FROM reservation_time
                WHERE id = ?
                """;

        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) ->
                new Time(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                ), id);
    }

    public List<Time> findAll() {
        String sql = """
                SELECT * FROM reservation_time
                """;
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Time time = new Time(
                    resultSet.getLong("id"),
                    LocalTime.parse(resultSet.getString("start_at"))
            );
            return time;
        });
    }

    public int delete(Long id) {
        String sql = """
                DELETE FROM reservation_time
                WHERE id = ?
                """;

        return jdbcTemplate.update(sql, id);
    }
}
