package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT
                    r.id AS reservation_id,
                    r.name AS reservation_name,
                    r.date AS reservation_date,
                    t.id AS time_id,
                    t.start_at AS time_start_at
                FROM reservation r
                INNER JOIN reservation_time t ON r.time_id = t.id
                """;

        RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
                rs.getLong("reservation_id"),
                rs.getString("reservation_name"),
                rs.getString("reservation_date"),
                new ReservationTime(
                        rs.getLong("time_id"),
                        rs.getString("time_start_at")
                )
        );
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Reservation save(String name, String date, Long timeId, ReservationTime time) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setLong(3, timeId);
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Reservation(id, name, date, time);
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
