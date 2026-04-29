package roomescape.repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<ReservationTime> rowMapper = (resultSet, rowColumn) -> ReservationTime.of(
        resultSet.getLong("id"),
        resultSet.getTime("start_at").toLocalTime()
    );

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("reservation_time")
            .usingGeneratedKeyColumns("id");
    }

    public ReservationTime create(ReservationTime time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", time.getStartAt());
        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return ReservationTime.of(id, time.getStartAt());
    }

    public List<ReservationTime> findAll() {
        String query = "select * from reservation_time";
        return jdbcTemplate.query(query, rowMapper);
    }


    public void deleteById(Long id) {
        String query = "delete from reservation_time where id = ?";
        jdbcTemplate.update(query, id);
    }
}
