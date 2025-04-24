package roomescape.data.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.business.domain.Time;
import roomescape.data.entity.TimeEntity;

@Repository
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;

    public TimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(final Time time) {
        final TimeEntity timeEntity = TimeEntity.from(time);
        final String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, timeEntity.startAt());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Time> findAll() {
        final String sql = "SELECT id, start_at FROM reservation_time";

        return jdbcTemplate.query(sql, TimeEntity.getDefaultRowMapper()).stream()
                .map(TimeEntity::toDomain)
                .toList();
    }
}
