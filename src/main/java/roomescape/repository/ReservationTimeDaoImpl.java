package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeAddRequest;

@Repository
public class ReservationTimeDaoImpl implements ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private RowMapper<ReservationTime> rowMapper = ((rs, rowNum) -> new ReservationTime(
            rs.getLong("id"),
            rs.getTime("start_at").toLocalTime()
    ));

    public ReservationTimeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RESERVATION_TIME")
                .usingGeneratedKeyColumns("id");;
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("select * from reservation_time", rowMapper);
    }

    @Override
    public ReservationTime insert(ReservationTimeAddRequest reservationTimeAddRequest) {
        Map<String, Object> reservationTimeRow = new HashMap<>();
        reservationTimeRow.put("start_at", reservationTimeAddRequest.getStartAt());
        Long id = simpleJdbcInsert.executeAndReturnKey(reservationTimeRow).longValue();
        return findById(id);
    }

    @Override
    public ReservationTime findById(Long id) {
        String sql = "select * from reservation_time where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }
}
