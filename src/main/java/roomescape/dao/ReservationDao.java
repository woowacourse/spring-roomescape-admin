package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> selectReservations() {
        final String sql = "SELECT\n"
                + "    r.id as reservation_id,\n"
                + "    r.name,\n"
                + "    r.date,\n"
                + "    t.id as time_id,\n"
                + "    t.start_at as time_value\n"
                + "FROM reservation as r\n"
                + "INNER JOIN reservation_time as t\n"
                + "  ON r.time_id = t.id";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> {
            ReservationTime time = new ReservationTime(
                    resultSet.getLong("time_id"),
                    resultSet.getString("time_value")
            );
            return new Reservation(
                    resultSet.getLong("reservation_id"),
                    resultSet.getString("name"),
                    resultSet.getString("date"),
                    time
            );
        });
    }

    public Reservation insertReservation(ReservationRequest request, ReservationTime time) {
        final String sql = "insert into reservation (name, date, time_id) values(?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,new String[]{"id"});
            ps.setString(1, request.getName());
            ps.setString(2, request.getDate());
            ps.setLong(3, time.getId());
            return ps;
        }, keyHolder);
        return new Reservation(keyHolder.getKey().longValue(), request.getName(), request.getDate(), time);
    }

    public void deleteReservation(Long id) {
        final String sql = "delete from reservation where id = ?;";
        jdbcTemplate.update(sql, id);
    }
}
