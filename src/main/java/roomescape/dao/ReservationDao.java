package roomescape.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Component
public class ReservationDao {

    private JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationResponse> getReservations() {
        String sql = "SELECT r.id, r.name, r.date, t.id, t.start_at FROM reservation AS r INNER JOIN reservation_time AS t ON reservation.time_id = reservation_time.id";

        List<ReservationResponse> reservations = jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
                    ReservationTime time = new ReservationTime(resultSet.getLong("t.id"), resultSet.getString("t.start_at"));
                    return new ReservationResponse(
                            resultSet.getLong("r.id"),
                            resultSet.getString("r.name"),
                            resultSet.getString("r.date"),
                            time
                    );
                });

        return reservations;
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, request.name());
            ps.setString(2, request.date());
            ps.setLong(3, request.timeId());
            return ps;
        }, keyHolder);

        ReservationTime reservationTime = findById(request.timeId());

        return new ReservationResponse(keyHolder.getKey().longValue(), request.name(), request.date(), reservationTime);
    }

    private ReservationTime findById(long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,
                (resultSet, rowNum) -> {
                    ReservationTime time = new ReservationTime(
                            resultSet.getLong("id"),
                            resultSet.getString("start_at")
                    );

                    return time;
                }, id);
    }

    public void deleteReservation(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }
}
