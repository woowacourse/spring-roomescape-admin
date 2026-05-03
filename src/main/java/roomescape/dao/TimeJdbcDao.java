package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dao.vo.TimeRow;
import roomescape.dao.vo.TimeRows;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TimeDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<TimeRow> rowMapper = (resultSet, rowNum) -> {
        TimeRow row = new TimeRow(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))
        );

        return row;
    };

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

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public Optional<TimeRow> findById(Long id) {
        String sql = """
                SELECT * FROM reservation_time
                WHERE id = ?
                """;

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, (resultSet, rowNum) ->
                new TimeRow(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                ), id));
    }

    public TimeRows findAll() {
        String sql = """
                SELECT * FROM reservation_time
                """;
        return new TimeRows(jdbcTemplate.query(sql, rowMapper));
    }

    public int delete(Long id) {
        String sql = """
                DELETE FROM reservation_time
                WHERE id = ?
                """;

        return jdbcTemplate.update(sql, id);
    }
}
