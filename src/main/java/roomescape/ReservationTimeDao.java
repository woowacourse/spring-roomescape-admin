package roomescape;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime insert(ReservationTime reservationTime) {
        final String sql = "insert into reservation_time (start_at) values(?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);
        return ReservationTime.withId(keyHolder.getKey().longValue(), reservationTime);
    }
}
