package roomescape.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTime;
import roomescape.repository.rowmapper.ReservationTimeRowMapper;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActor;
    private final ReservationTimeRowMapper rowMapper;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate, ReservationTimeRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActor = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
        this.rowMapper = rowMapper;
    }

    public ReservationTime save(ReservationTimeRegisterDetail registerDetail) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("start_at", registerDetail.startAt());
        Long savedTimeId = insertActor.executeAndReturnKey(parameters).longValue();

        return registerDetail.toEntity(savedTimeId);
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";

        return jdbcTemplate.query(sql, rowMapper);
    }

    public ReservationTime findById(long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void deleteById(long timeId) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";

        jdbcTemplate.update(sql, timeId);
    }
}
