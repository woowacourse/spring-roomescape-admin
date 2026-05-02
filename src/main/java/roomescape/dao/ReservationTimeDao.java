package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationTime> rowMapper = (rs, rowNum) -> new ReservationTime(
            rs.getLong("id"),
            rs.getString("start_at")
    );

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> getTimes() {
        return jdbcTemplate.query("SELECT id, start_at FROM reservation_time", rowMapper);
    }

    public Long insertAndGetId(ReservationTimeRequest request) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO reservation_time (start_at) VALUES (?)",
                    new String[]{"id"}
            );
            ps.setString(1, request.startAt());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public ReservationTime findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, start_at FROM reservation_time WHERE id = ?",
                rowMapper,
                id
        );
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }
}
