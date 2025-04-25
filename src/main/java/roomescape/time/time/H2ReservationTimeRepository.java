package roomescape.time.time;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.ReservationTime;

@Repository
@RequiredArgsConstructor
public class H2ReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long save(final ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_times (start_at) VALUE (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<ReservationTime> findById(final Long id) {
        String sql = "SELECT * FROM reservation_times WHERE id = ?";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(sql, (rs, rowNum) ->
                        new ReservationTime(rs.getLong("id"),
                                rs.getTime("start_at").toLocalTime()),
                id
        );

        if (!reservationTimes.isEmpty()) {
            return Optional.of(reservationTimes.getFirst());
        }
        return Optional.empty();
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_times";
        return jdbcTemplate.query(sql, (resultSet, rowCount) ->
                new ReservationTime(resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime())
        );
    }

    @Override
    public void delete(final ReservationTime reservationTime) {
        String sql = "DELETE FROM reservation_times WHERE id = ?";
        jdbcTemplate.update(sql, reservationTime.getId());
    }
}
