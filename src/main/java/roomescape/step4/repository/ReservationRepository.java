package roomescape.step4.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.step4.domain.Reservation;
import roomescape.step4.domain.ReservationTime;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository("ReservationRepositoryStep4")
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = "SELECT r.id, r.name, r.date, r.time_id, rt.start_at " +
                "FROM reservation_v2 r " +
                "JOIN reservation_time rt ON r.time_id = rt.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Reservation(
                    rs.getLong("id"),
                    rs.getString("name"),
                    LocalDate.parse(rs.getString("date")),
                    new ReservationTime(
                            rs.getLong("time_id"),
                            LocalTime.parse(rs.getString("start_at"))));
        });
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation_v2(name, date, time_id) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTime().getId());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new Reservation(
            id,
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime()
        );
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation_v2 where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
