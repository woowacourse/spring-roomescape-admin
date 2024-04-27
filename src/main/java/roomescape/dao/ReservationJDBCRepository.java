package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import java.util.List;
import java.util.Map;

@Repository
public class ReservationJDBCRepository implements ReservationRepository {
    private static final String TABLE_NAME = "reservation";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Reservation> rowMapper = (resultSet, rowNum) -> {
        ReservationTime reservationTime = new ReservationTime(resultSet.getLong("time_id"), resultSet.getString("start_at"));
        Reservation reservation = new Reservation(
                resultSet.getLong("reservation_id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                reservationTime);
        return reservation;
    };

    public ReservationJDBCRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Reservation> findAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.start_at FROM reservation as r inner join reservation_time as t on r.time_id = t.id";
        List<Reservation> reservations = jdbcTemplate.query(sql, rowMapper);
        return reservations;
    }

    @Override
    public Reservation save(final Reservation reservation) {
        Map<String, ?> params = Map.of(
                "name", reservation.getName(),
                "date", reservation.getDate(),
                "time_id", reservation.getReservationTime().getId());
        long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Reservation(id, reservation);
    }

    @Override
    public void deleteById(final long id) {
        String sql = "DELETE FROM Reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
