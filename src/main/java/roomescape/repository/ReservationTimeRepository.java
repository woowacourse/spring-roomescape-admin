package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime create(ReservationTime reservationTime) {
        String createSql = "INSERT INTO reservation_time (start_at) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, reservationTime.getStartAt());

            return statement;
        }, keyHolder);

        Number id = keyHolder.getKey();
        validateNotNull(id);

        return reservationTime.with(id.longValue());
    }

    private void validateNotNull(Number id) {
        if (id == null) {
            throw new InvalidDataAccessApiUsageException("ID 조회에 실패했습니다.");
        }
    }
}
