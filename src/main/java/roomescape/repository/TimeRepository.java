package roomescape.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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

    public List<ReservationTime> findAll() {
        String findAllSql = "SELECT id, start_at FROM reservation_time";

        return jdbcTemplate.query(findAllSql,
                (rs, rowNum) -> ReservationTime.of(
                        rs.getLong("id"),
                        LocalTime.parse(rs.getString("start_at"))
                )
        );
    }

    public void deleteById(Long id) {
        String deleteSql = "DELETE FROM reservation_time WHERE id=?";
        jdbcTemplate.update(deleteSql, id);
    }
}
