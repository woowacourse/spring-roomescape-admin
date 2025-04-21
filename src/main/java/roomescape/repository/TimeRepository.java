package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String insertSql = "INSERT INTO reservation_time(start_at) VALUES(?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    insertSql,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("예약 저장 중 id 생성 실패");
        }
        return key.longValue();
    }

}
