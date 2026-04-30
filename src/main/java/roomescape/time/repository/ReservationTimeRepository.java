package roomescape.time.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.ReservationTime;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.time.LocalTime;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ReservationTime save(LocalTime startAt) {
        String sql = "INSERT INTO reservation_time (start_At) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setObject(1, startAt);
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        return new ReservationTime(id, startAt);
    }
}
