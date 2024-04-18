package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
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

    public Long insert(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into reservation_time(start_at) values (?)",
                            new String[]{"id"});
                    ps.setString(1, reservationTime.getStartAt().toString());
                    return ps;
                }, keyHolder
        );

        return keyHolder.getKey().longValue();
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("select * from reservation_time",
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                )
        );
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }

    public ReservationTime findById(Long id) {
        return jdbcTemplate.queryForObject("select * from reservation_time where id = ?",
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                ), id
        );
    }
}
