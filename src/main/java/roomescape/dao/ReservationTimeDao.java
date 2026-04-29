package roomescape.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@Component
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTimeResponse create(ReservationTimeRequest reservationTimeRequest) {
        String sql = "INSERT INTO reservation_time(start_at) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    ps.setString(1, reservationTimeRequest.startAt());
                    return ps;
                }, keyHolder
        );

        return new ReservationTimeResponse(keyHolder.getKey().longValue(), reservationTimeRequest.startAt());
    }

    public List<ReservationTimeResponse> getTimes() {
        String sql = "SELECT * FROM reservation_time";

        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(
                            resultSet.getLong("id"),
                            resultSet.getString("start_at")
                    );
                    return reservationTimeResponse;
                });
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
