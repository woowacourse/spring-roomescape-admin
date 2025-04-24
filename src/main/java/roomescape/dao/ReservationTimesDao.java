package roomescape.dao;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimesDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    }

    public ReservationTime findById(Long id) {
        return jdbcTemplate.queryForObject(
            "select * from reservation_time where id = ?",
            (resultSet, rowNum) ->
                new ReservationTime(
                    id,
                    LocalTime.parse(
                        resultSet.getString("start_at"),
                        DateTimeFormatter.ofPattern("HH:mm")
                    )
                ),
            id
        );
    }

    public ReservationTime create(ReservationTime reservationTime) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("start_at", reservationTime.getStartAt().toString());
        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new ReservationTime(id, reservationTime.getStartAt());
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query(
            "select * from reservation_time",
            (resultSet, rowNum) -> {
                LocalTime startAt = LocalTime.parse(
                    resultSet.getString("start_at"),
                    DateTimeFormatter.ofPattern("HH:mm")
                );
                return new ReservationTime(
                    resultSet.getLong("id"),
                    startAt
                );
            });
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(
            "delete from reservation_time where id = ?",
            id
        );
    }
}
