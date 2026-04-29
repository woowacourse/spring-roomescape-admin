package roomescape.reservation.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.reservation.entity.Reservation;
import roomescape.time.entity.ReservationTime;

import java.util.List;

@Repository
public class QueryingDao {

    private final JdbcTemplate jdbcTemplate;

    public QueryingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> {
        ReservationTime time = new ReservationTime(
                resultSet.getLong("time_id"),
                resultSet.getString("start_at")
        );

        return new Reservation(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("date").toLocalDate(),
                time
        );
    };

    public int count() {
        String sql = "select count(*) from reservation";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Reservation findById(Long id) {
        String sql = "select r.id, r.name, r.date, r.time_id, t.start_at " +
                "from reservation r " +
                "inner join reservation_time t on r.time_id = t.id " +
                "where r.id = ?";
        return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
    }

    public List<Reservation> findAll() {
        String sql = "select r.id, r.name, r.date, r.time_id, t.start_at " +
                "from reservation r " +
                "inner join reservation_time t on r.time_id = t.id";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }
}
