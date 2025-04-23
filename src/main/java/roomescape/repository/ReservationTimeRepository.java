package roomescape.repository;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime save(ReservationTime time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO reservation_time(start_at) VALUES(?)",
                    new String[] {"id"});
            ps.setString(1, time.getStartAt().toString());
            return ps;
        }
        , keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new ReservationTime(id, time.getStartAt());
    }
}
