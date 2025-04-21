package roomescape.dao;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
            .withTableName("reservation_time")
            .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> findAllReservationTimes() {
        String query = "select * from reservation_time";
        return jdbcTemplate.query(query,
            (resultSet, RowNum) -> {
                ReservationTime reservationTime = new ReservationTime(
                    resultSet.getLong("id"),
                    LocalTime.parse(resultSet.getString("start_at")));
                return reservationTime;
            });
    }

    public void saveReservationTime(ReservationTime reservationTime) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("start_at", reservationTime.getTime());
        Number newId = insertActor.executeAndReturnKey(parameters);
        reservationTime.setId(newId.longValue());
    }

    public void deleteReservationTime(Long id) {
        String query = "delete from reservation_time where id = ?";
        jdbcTemplate.update(query, id);
    }
}
