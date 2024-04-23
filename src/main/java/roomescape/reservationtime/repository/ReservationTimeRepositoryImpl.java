package roomescape.reservationtime.repository;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservationtime.domain.ReservationTime;

@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ReservationTime> reservationTimeRowMapper =
            (resultSet, rowNum) -> new ReservationTime(
                    resultSet.getLong("id"),
                    resultSet.getTime("start_at").toLocalTime()
            );

    public Long save(final ReservationTime reservationTime) {
        String sql = "insert into reservation_time (start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setTime(1, Time.valueOf(reservationTime.getTime()));
            return ps;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(sql, reservationTimeRowMapper);
    }

    @Override
    public void deleteById(final Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public ReservationTime findById(final Long timeId) {
        String sql = "select id, start_at from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(sql, reservationTimeRowMapper, timeId);
    }
}
