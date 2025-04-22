package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeH2Repository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeH2Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(ReservationTime reservationTime) {
        String query = "INSERT into reservation_time(start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        reservationTime.setId(id);

        return id;
    }

    @Override
    public List<ReservationTime> findAll() {
        String query = "SELECT * FROM reservation_time";

        return jdbcTemplate.query(query, ((rs, rowNum) ->
                new ReservationTime(rs.getLong("id"), rs.getTime("start_at").toLocalTime()))
        );
    }

    @Override
    public void deleteById(final Long id) {
        String query = "DELETE FROM reservation_time WHERE id = ?";
        int update = jdbcTemplate.update(query, id);

        if (update == 0) {
            throw new IllegalArgumentException("id에 해당하는 예약 시간이 없습니다.");
        }
    }

    @Override
    public Optional<ReservationTime> findById(Long id) {
        String query = "SELECT * FROM reservation_time WHERE id = ?";

        ReservationTime reservationTime = jdbcTemplate.queryForObject(query, (rs, rowNum) ->
                new ReservationTime(rs.getLong("id"), rs.getTime("start_at").toLocalTime())
        );

        return Optional.ofNullable(reservationTime);
    }
}
