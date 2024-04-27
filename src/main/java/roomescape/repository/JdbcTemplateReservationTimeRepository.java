package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class JdbcTemplateReservationTimeRepository implements ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateReservationTimeRepository(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (resultSet, rowNum) -> new ReservationTime(
            resultSet.getLong("id"),
            resultSet.getTime("start_at").toLocalTime()
    );

    @Override
    public ReservationTime save(ReservationTime reservationTimeRequest) {
        Map<String, Object> params = new HashMap<>();
        params.put("start_at", reservationTimeRequest.getStartAt());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return findById(id);
    }

    @Override
    public ReservationTime findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, start_at FROM reservation_time WHERE id = ?",
                reservationTimeRowMapper, id);
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("SELECT id, start_at FROM reservation_time", reservationTimeRowMapper);
    }

    @Override
    public void deleteById(Long id) {
        int rowCount = jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
        if (rowCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 시간입니다.");
        }
    }
}
