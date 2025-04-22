package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.AddTimeDto;
import roomescape.model.Time;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Time addTime(AddTimeDto addTimeDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into reservation_time (start_at) values (?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, addTimeDto.start_at());
            return ps;
        }, keyHolder);
        return AddTimeDto.toEntity(keyHolder.getKey().longValue(), addTimeDto);
    }

    public List<Time> getAllTime() {
        String sql = "select * from reservation_time";
        List<Time> Times = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Time time = new Time(
                    rs.getLong("id"),
                    rs.getString("start_at")
            );
            return time;
        });
        return Times;
    }

    public Integer deleteTime(Long id) {
        return jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }

}
