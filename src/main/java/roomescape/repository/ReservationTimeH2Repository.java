package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeH2Repository implements ReservationTimeRepository {

    public static final String SELECT_RESERVATION_TIME = "SELECT * FROM reservation_time";
    public static final RowMapper<ReservationTime> RESERVATION_TIME_ROW_MAPPER = (rs, rowNum) -> new ReservationTime(
            rs.getLong("id"),
            rs.getTime("start_at").toLocalTime()
    );

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeH2Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(final ReservationTime reservationTime) {
        String query = "INSERT into reservation_time(start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        reservationTime.setId(id);

        return id;
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query(SELECT_RESERVATION_TIME, RESERVATION_TIME_ROW_MAPPER);
    }

    @Override
    public void deleteById(final Long id) {
        String query = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    @Override
    public Optional<ReservationTime> findById(final Long id) {
        String query = SELECT_RESERVATION_TIME
                + " WHERE id = ?";

        List<ReservationTime> result = jdbcTemplate.query(query, RESERVATION_TIME_ROW_MAPPER, id);

        return result.stream().findAny();
    }

}
