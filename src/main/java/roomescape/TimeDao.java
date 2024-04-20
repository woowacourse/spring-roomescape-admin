package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.domain.Time;
import roomescape.dto.TimeRequestDto;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class TimeDao {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public @Autowired TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(TimeRequestDto timeRequestDto) {
        String insertSql = "INSERT INTO reservation_time(start_at) VALUES ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    insertSql,
                    new String[]{"id"});
            ps.setString(1, timeRequestDto.startAt());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Time> findAll() {
        String findAllSql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(findAllSql,
                (resultSet, numRow) -> new Time(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                ));
    }

    public void deleteById(Long id) {
        String deleteSql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(deleteSql, id);
    }
}
