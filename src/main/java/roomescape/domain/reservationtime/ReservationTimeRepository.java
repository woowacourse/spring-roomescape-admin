package roomescape.domain.reservationtime;

import java.sql.PreparedStatement;
import java.sql.Statement;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationTimeRepository {

    private static final String INSERT_SQL =
        "insert into reservation_time(start_at) values (?)";

    private final JdbcTemplate jdbcTemplate;

    public ReservationTime save(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservationTime.getStartAt().toString());
            return ps;
        }, keyHolder);
        long id = extractId(keyHolder);
        return ReservationTime.createWithId(id, reservationTime);
    }

    private long extractId(KeyHolder keyHolder) {
        if (keyHolder.getKey() == null) {
            throw new IllegalStateException("생성 키를 조회할 수 없습니다.");
        }
        return keyHolder.getKey().longValue();
    }
}
