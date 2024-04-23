package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ReservationTime save(ReservationTime time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                "insert into reservation_time (start_at) values (?)",
                new String[]{"id"}
            );
            ps.setString(1, time.getStartAt());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return findById(id);
    }

    public ReservationTime findById(Long id) {
        return jdbcTemplate.queryForObject(
            "select id, start_at from reservation_time where id=?",
            (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
            ), id
        );
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query(
            "select id, start_at from reservation_time",
            (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
            )
        );
    }

    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }
}
