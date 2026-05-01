package roomescape.reservation.time.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.time.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTime findById(Long timeId) {
        String sql = "SELECT * FROM reservation_time WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,
                (resultSet, rowNum) -> ReservationTime.of(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()
                ), timeId);
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";

        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> ReservationTime.of(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()
                )
        );
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt().toString());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("reservation_time 저장 후 생성된 ID를 반환받지 못했습니다.");
        }
        long timeId = key.longValue();

        return ReservationTime.of(timeId, reservationTime.getStartAt());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
