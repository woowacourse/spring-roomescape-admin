package roomescape.repository;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.Reservation;
import roomescape.dto.ReservationRequest;

@Repository
public class ReservationDao {
    private JdbcTemplate jdbcTemplate;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation save(final ReservationRequest reservationRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into reservation (name, date, time_id) values (?, ?, ?)",
                            new String[]{"id"}
                    );
                    ps.setString(1, reservationRequest.name());
                    ps.setString(2, reservationRequest.date().toString());
                    ps.setString(3, String.valueOf(reservationRequest.timeId()));
                    return ps;
                }, keyHolder
        );

        long reservationId = keyHolder.getKey().longValue();
        return get(reservationId);
    }

    private Reservation get(long id) {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value "
                + "FROM reservation as r "
                + "INNER JOIN reservation_time as t "
                + "ON r.time_id = t.id "
                + "WHERE r.id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        return Reservation.of(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getLong("time_id"),
                resultSet.getString("start_at")
        );
    };

    public List<Reservation> getAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at as time_value "
                + "FROM reservation as r "
                + "INNER JOIN reservation_time AS t "
                + "ON r.time_id = t.id";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> Reservation.of(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        resultSet.getLong("time_id"),
                        resultSet.getString("start_at")
                )
        );
    }

    public void delete(final long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", Long.valueOf(id));
    }
}
