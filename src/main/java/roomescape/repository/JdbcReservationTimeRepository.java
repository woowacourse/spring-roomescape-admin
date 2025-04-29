package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcReservationTimeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("RESERVATION_TIME")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        String time = reservationTime.getStartAt().toString();
        Map<String, String> params = Map.of("start_at", time);
        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return reservationTime.assignId(id);
    }

    @Override
    public ReservationTime findById(Long id) {
        return jdbcTemplate.queryForObject("select * from reservation_time where id = ?", reservationTimeRowMapper(),
                id);
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("select * from reservation_time", reservationTimeRowMapper());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }

    private RowMapper<ReservationTime> reservationTimeRowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String startAt = rs.getString("start_at");
            LocalTime time = LocalTime.parse(startAt);
            return ReservationTime.of(id, time);
        };
    }
}
