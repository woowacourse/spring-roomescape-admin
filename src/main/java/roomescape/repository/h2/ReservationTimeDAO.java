package roomescape.repository.h2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

@Repository
public class ReservationTimeDAO implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert insertActor;

    public ReservationTimeDAO(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<ReservationTime> findAllReservationTimes() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) ->
                new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()
                ));
    }

    @Override
    public ReservationTime findReservationById(long id) {
        String sql = "select * from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) ->
                new ReservationTime(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()
                ), id);
    }

    @Override
    public ReservationTime addReservationTime(ReservationTime reservationTime) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("start_at", reservationTime.getStartAt());
        Number newId = insertActor.executeAndReturnKey(parameters);
        return new ReservationTime(newId.longValue(), reservationTime.getStartAt());
    }

    @Override
    public void deleteReservationTime(long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);

    }
}
