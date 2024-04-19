package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT a.id AS reservation_id, a.name AS name, a.date AS date, t.id AS time_id, t.start_at AS start_at
                FROM reservation AS a
                LEFT JOIN reservation_time AS t ON a.time_id = t.id""";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            ReservationTime reservationTime = new ReservationTime(resultSet.getLong("time_id"),
                    LocalTime.parse(resultSet.getString("start_at")));

            return new Reservation(resultSet.getLong("reservation_id"), resultSet.getString("name"),
                    LocalDate.parse(resultSet.getString("date")), reservationTime);
        });
    }

    public Long insert(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate().toString());
            ps.setLong(3, reservation.getTimeId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void deleteByTimeId(Long timeId) {
        String sql = "DELETE FROM reservation WHERE time_id = ?";
        jdbcTemplate.update(sql, timeId);
    }
}
