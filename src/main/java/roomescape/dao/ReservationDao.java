package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
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

    public long create(Reservation reservation) {
        String sql = "INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime().getId().toString());
            return ps;
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public List<Reservation> getAll() {
        String sql = """
                SELECT
                r.id AS reservation_id,
                r.name,
                r.date,
                t.id AS time_id,
                t.start_at AS time_value
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                ON r.time_id = t.id
                """;
        RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
            long reservationId = resultSet.getLong("reservation_id");
            String name = resultSet.getString("name");
            String date = resultSet.getString("date");
            long timeId = resultSet.getLong("time_id");
            String startAt = resultSet.getString("time_value");
            return new Reservation(reservationId, name, date, new ReservationTime(timeId, startAt));
        };
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void delete(long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
