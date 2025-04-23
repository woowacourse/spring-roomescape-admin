package roomescape.reservation.repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservation.model.ReservationTime;
import roomescape.reservation.model.ReservationTimeDetails;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime insertTime(ReservationTimeDetails reservationTimeDetails) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", reservationTimeDetails.startAt());
        Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new ReservationTime(number.longValue(), reservationTimeDetails.startAt());
    }

    public List<ReservationTime> findAll() {
        String sql = "Select * from reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getObject("start_at", LocalTime.class)
        ));
    }

    public boolean deleteTimeById(long id) {
        String sql = "delete from reservation_time where id = ?";
        int updated = jdbcTemplate.update(sql, id);
        return updated != 0;
    }

    public Optional<ReservationTime> findById(long id) {
        String sql = "select * from reservation_time where id = ?";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getObject("start_at", LocalTime.class)
        ), id).stream().findFirst();
    }
}
