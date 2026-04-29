package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.request.ReservationTimeRequest;
import roomescape.response.ReservationTimeResponse;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class JdbcTemplateReservationTimeRepository implements ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTimeResponse addTime(ReservationTimeRequest request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO reservation_time(start_at) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setTime(1, java.sql.Time.valueOf(request.startAt()));
            return preparedStatement;
        }, keyHolder);

        return new ReservationTimeResponse(
                Objects.requireNonNull(keyHolder.getKey()).longValue(),
                request.startAt());
    }

    @Override
    public List<ReservationTimeResponse> findAllReservationTimes() {
        return jdbcTemplate.query("SELECT id, start_at FROM reservation_time",
                (rs, rowNum) -> new ReservationTimeResponse(
                        rs.getLong("id"),
                        rs.getTime("start_at").toLocalTime()
                ));
    }

    @Override
    public void deleteTime(Long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ? ", id);
    }
}
