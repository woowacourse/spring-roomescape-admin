package roomescape.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservationtime.ReservationTime;

@Repository
public class ReservationTimeRepository {

    private static final int DELETE_NO_ROWS_AFFECTED = 0;
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(final ReservationTime time) {
        final String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, time.getStartAt().toString());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public boolean existsBy(LocalTime startAt) {
        final String sql = "SELECT EXISTS(SELECT 1 FROM reservation_time WHERE start_at = ?)";
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, startAt);
        return Boolean.TRUE.equals(exists);
    }

    public List<ReservationTime> findAll() {
        final String sql = "SELECT id, start_at FROM reservation_time";

        return jdbcTemplate.query(sql, getReservationTimeRowMapper());
    }

    public ReservationTime findBy(final Long id) {
        final String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, getReservationTimeRowMapper(), id);
    }

    private RowMapper<ReservationTime> getReservationTimeRowMapper() {
        return (resultSet, rowNum) -> mapRow(resultSet);
    }

    public boolean deleteBy(final Long id) {
        final String sql = "DELETE FROM reservation_time where id = ?";
        return jdbcTemplate.update(sql, id) != DELETE_NO_ROWS_AFFECTED;
    }

    public ReservationTime mapRow(ResultSet resultSet) throws SQLException {
        return new ReservationTime(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))
        );
    }
}
