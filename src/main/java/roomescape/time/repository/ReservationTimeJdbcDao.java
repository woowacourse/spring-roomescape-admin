package roomescape.time.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.ReservationTime;

@Repository
public class ReservationTimeJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime findById(Long id) {
        String sql = "select id, start_at from reservation_time where id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> {
                    ReservationTime reservationTime = ReservationTime.create(
                            resultSet.getLong("id"),
                            resultSet.getTime("start_at").toLocalTime()
                    );

                    return reservationTime;
                }, id);
    }

    public List<ReservationTime> findAll() {
        String sql = "select id, start_at from reservation_time";

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    ReservationTime reservationTime = ReservationTime.create(
                            resultSet.getLong("id"),
                            resultSet.getTime("start_at").toLocalTime()
                    );

                    return reservationTime;
                });
    }

    public Long save(ReservationTime reservationTime) {
        String sql = "insert into reservation_time (start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int deleteById(Long id) {
        String sql = "delete from reservation_time where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
