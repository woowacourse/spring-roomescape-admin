package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import roomescape.data.vo.ReservationTime;

@Component
public class ReservationTimeDaoImpl implements ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(ReservationTime reservationTime) {
        final var sql = "INSERT INTO reservation_time(start_at) values (?)";
        final var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, new String[] {"id"});
            pstmt.setTime(1, java.sql.Time.valueOf(reservationTime.getStartAt()));
            return pstmt;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<ReservationTime> findAll() {
        final var sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, actorRowMapper());
    }

}
