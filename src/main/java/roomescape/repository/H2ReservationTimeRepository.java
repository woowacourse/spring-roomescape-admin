package roomescape.repository;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class H2ReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public H2ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM Reservation_Time";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new ReservationTime(
                        resultSet.getInt("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                )
        );
    }

    @Override
    public ReservationTime add(ReservationTime reservationTime) {
        Map<String, String> params = new HashMap<>();
        params.put("start_at", reservationTime.getStartAt().format(DateTimeFormatter.ofPattern("HH:mm")));
        int id = jdbcInsert.executeAndReturnKey(params).intValue();
        return reservationTime.createWithId(id);
    }

    @Override
    public void removeById(int id) {
        String sql = "DELETE FROM Reservation_Time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
