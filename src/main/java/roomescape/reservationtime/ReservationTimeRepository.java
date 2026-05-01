package roomescape.reservationtime;

import java.sql.PreparedStatement;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(ReservationTimeRepository.class);

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(ReservationTime reservationTime) {

        String sql = "insert into reservation_time (start_at) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void delete(Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<ReservationTime> findAllReservationTimes() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    ReservationTime reservationTime = new ReservationTime(
                            resultSet.getLong("id"),
                            resultSet.getString("start_at")
                    );
                    return reservationTime;
                });
    }

    public ReservationTime findById(Long id) {
        logger.info("id = {}", id);
        String sql = "select id, start_at from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) ->
                        new ReservationTime(
                                resultSet.getLong("id"),
                                resultSet.getString("start_at")), id);
    }
}
