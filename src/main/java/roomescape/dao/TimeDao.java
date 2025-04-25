package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.domain_entity.Id;
import roomescape.domain_entity.ReservationTime;
import roomescape.mapper.TimeMapper;

@Component
public class TimeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public long create(ReservationTime time) {
        String sql = "insert into reservation_time (start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            sql,
                            new String[]{"id"}
                    );
                    ps.setObject(1, time.getStartAt());
                    return ps;
                }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    public List<ReservationTime> findAll() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(
                sql,
                new TimeMapper()
        );
    }

    public ReservationTime findById(long id) {
        String sql = "select id, start_at from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new TimeMapper(),
                id
        );
    }

    public void delteById(Id id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(
                sql,
                id.value()
        );
    }
}
