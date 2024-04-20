package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import roomescape.dto.TimeRequestDto;

import java.sql.PreparedStatement;

public class TimeDao {
    private final JdbcTemplate jdbcTemplate;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(TimeRequestDto timeRequestDto) {
        String insertSql = "INSERT INTO reservation_time(start_at) VALUES ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    insertSql,
                    new String[]{"id"});
            ps.setString(1, timeRequestDto.start_at());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
