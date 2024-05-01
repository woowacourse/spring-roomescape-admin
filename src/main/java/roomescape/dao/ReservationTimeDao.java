package roomescape.dao;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import java.util.Objects;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import roomescape.data.vo.ReservationTime;

@Component
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static RowMapper<ReservationTime> reservationTimeRowMapper() {
        return (resultSet, rowNum) -> new ReservationTime(resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime());
    }

    public long save(final ReservationTime reservationTime) {
        final var sql = "INSERT INTO reservation_time(start_at) values (?)";
        final var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"id"});
            pstmt.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return pstmt;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public List<ReservationTime> findAll() {
        final var sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, reservationTimeRowMapper());
    }

    public void delete(final long id) {
        try {
            final var sql = "DELETE FROM reservation_time WHERE id = ?";
            jdbcTemplate.update(sql, id);
        } catch (final DataIntegrityViolationException e) {
            throw new IllegalStateException(String.format("%d는 사용중입니다.", id));
        }
    }

    public ReservationTime findOne(final long id) {
        try {
            final var sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, reservationTimeRowMapper(), id);
        } catch (final EmptyResultDataAccessException e) {
            throw new IllegalArgumentException(String.format("%d는 없는 id값입니다.", id));
        }
    }
}
