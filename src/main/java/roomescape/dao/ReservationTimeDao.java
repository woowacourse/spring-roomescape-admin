package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.reservation.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class ReservationTimeDao {
    private JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ReservationTime> timeRowMapper = ((rs, rowNum) ->
            new ReservationTime(rs.getLong("id"), rs.getString("start_at")));

    public Long add(ReservationTime time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO RESERVATION_TIME(start_at) VALUES(?)",
                    new String[]{"id"});
            ps.setString(1, ReservationTime.formattedTime(time.getTime()));
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public ReservationTime findById(Long id) {
        String sql = "SELECT * FROM RESERVATION_TIME WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, timeRowMapper, id);
    }

    public List<ReservationTime> findAllTimes() {
        String sql = "SELECT * FROM RESERVATION_TIME";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM RESERVATION_TIME WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
