package roomescape.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QueryingDAO {
    private final JdbcTemplate jdbcTemplate;

    public QueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> findAllReservations() {
        String sql = "select id, name, date, time from reservation";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    return new Reservation(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("date"),
                            rs.getString("time")
                    );
                });
    }

    public Reservation findReservationById(Long id) {
        String sql = "select id, name, date, time from reservation where id = ?";

        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> {
                    Reservation reservation = new Reservation(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("date"),
                            rs.getString("time")
                    );
                    return reservation;
                }, id);
    }
}
