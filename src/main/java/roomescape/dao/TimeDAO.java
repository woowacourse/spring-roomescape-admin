package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.TimeCreateRequestDto;
import roomescape.model.Time;

@Repository
public class TimeDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TimeDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Time> findAllTimes() {
        return jdbcTemplate.query(
                "SELECT * FROM reservation_time",
                (resultSet, rowNum) -> new Time(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                ));
    }

    public Time insert(TimeCreateRequestDto timeCreateRequestDto) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[]{"id"});
            ps.setString(1, timeCreateRequestDto.startAt());
            return ps;
        }, keyHolder);
        return new Time(keyHolder.getKey().longValue(), timeCreateRequestDto.startAt());
    }
}
