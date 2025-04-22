package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class JdbcTimeRepository implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final static RowMapper<ReservationTime> RESERVATION_TIME_ROW_MAPPER =
            (rs, rowNum) -> ReservationTime.of(
                    rs.getLong("id"),
                    rs.getTime("start_at").toLocalTime()
            );

    @Override
    public Long save(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String insertSql = "INSERT INTO reservation_time(start_at) VALUES(?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    insertSql,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("예약 저장 중 id 생성 실패");
        }
        return key.longValue();
    }

    @Override
    public List<ReservationTime> findAll() {
        String findAllSql = "SELECT id, start_at FROM reservation_time";

        return jdbcTemplate.query(findAllSql, RESERVATION_TIME_ROW_MAPPER);
    }

    @Override
    public ReservationTime findById(Long id) {
        String selectOneSql = "SELECT id, start_at FROM reservation_time WHERE id=?";
        return jdbcTemplate.queryForObject(selectOneSql, RESERVATION_TIME_ROW_MAPPER,
                id);
    }

    @Override
    public void deleteById(Long id) {
        String deleteSql = "DELETE FROM reservation_time WHERE id=?";
        jdbcTemplate.update(deleteSql, id);
    }
}
