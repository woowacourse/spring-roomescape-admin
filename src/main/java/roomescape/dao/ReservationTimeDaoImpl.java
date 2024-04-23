package roomescape.dao;

import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import roomescape.domain.ReservationTime;

@Component
public class ReservationTimeDaoImpl implements ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("SELECT * FROM reservation_time",
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        rs.getTime("start_at")
                ));
    }

    @Override
    public ReservationTime findById(long id) {
        if (existsById(id)) {
            return jdbcTemplate.queryForObject("SELECT * FROM reservation_time WHERE id = ?",
                    (rs, rowNum) -> new ReservationTime(
                            rs.getLong("id"),
                            rs.getTime("start_at")
                    ), id);
        }
        throw new IllegalArgumentException("id doesn't exist");
    }

    @Override
    public void save(ReservationTime reservationTime) {
        if (existsById(reservationTime.getId())) {
            throw new IllegalArgumentException("duplicated id exists");
        }
        jdbcTemplate.update("INSERT INTO reservation_time (id, start_at) VALUES (?, ?)",
                reservationTime.getId(), Time.valueOf(reservationTime.getStartAt()));
    }

    @Override
    public boolean deleteById(long id) {
        boolean exists = existsById(id);
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
        return exists;
    }

    private boolean existsById(long id) {
        long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM reservation_time WHERE id = ?", Long.class, id);
        return count > 0;
    }
}
