package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class H2ReservationTimeDao implements ReservationTimeDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public H2ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ReservationTime(
                        rs.getLong("id"),
                        rs.getString("start_at")
                ));
    }

    @Override
    public Optional<ReservationTime> findById(long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";

        return jdbcTemplate.query(sql,
                        (rs, rowNum) -> new ReservationTime(
                                rs.getLong("id"),
                                rs.getString("start_at")
                        ), id).stream()
                .findAny();
    }

    @Override
    public ReservationTime save(ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationTime.getStartAt());
            return ps;
        }, keyHolder);

        return new ReservationTime(keyHolder.getKeyAs(Long.class), reservationTime.getStartAt());
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
