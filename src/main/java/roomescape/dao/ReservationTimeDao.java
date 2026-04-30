package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ReservationTime> actorRowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(
                resultSet.getLong("id"),
                ReservationTime.parse(resultSet.getString("start_at"))
        );
        return reservationTime;
    };

    public List<ReservationTime> findAll() {
        String sql = "select id, start_at from reservation_times";

        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public Long insertReservationTime(String startAt) {
        String sql = "insert into reservation_times (start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, startAt);
            return ps;
        }, keyHolder);

        Long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return generatedId;
    }

    public void deleteById(Long id) {
        String sql = "delete from reservation_times where id = ?";

        jdbcTemplate.update(sql, id);
    }

    public ReservationTime findById(Long id) {
        String readSql = "select id, start_at from reservation_times where id = ?";

        return jdbcTemplate.queryForObject(readSql, actorRowMapper, id);
    }
}
