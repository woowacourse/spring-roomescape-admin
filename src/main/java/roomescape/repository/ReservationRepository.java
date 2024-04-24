package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Repository
public class ReservationRepository {
    private static final RowMapper<Reservation> actorRowMapper = (resultSet, rowNum) -> {
        ReservationTime time = new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getTime("time_value").toLocalTime()
        );

        return new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                time
        );
    };
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("RESERVATION")
                .usingGeneratedKeyColumns("id");
    }

    public Long createReservation(Reservation reservation) {
        Map<String, String> params = new HashMap<>();
        params.put("name", reservation.getName());
        params.put("date", reservation.getDate().toString());
        params.put("time_id", reservation.getTime().getId().toString());

        return jdbcInsert.executeAndReturnKey(params).longValue();
    }

    public List<Reservation> readReservations() {
        String sql = """
                SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at AS time_value
                FROM reservation AS r
                INNER JOIN reservation_time AS t ON r.time_id = t.id""";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public Reservation readReservationById(Long id) {
        String sql = """
                SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.start_at AS time_value
                FROM reservation AS r
                INNER JOIN reservation_time AS t ON r.time_id = t.id
                WHERE r.id = ?""";
        return jdbcTemplate.queryForObject(sql, actorRowMapper, id);
    }

    public Long deleteReservation(Long id) {
        String sql = "delete from reservation where id = ?";
        return (long) jdbcTemplate.update(sql, id);
    }
}
